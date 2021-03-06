package devops.workshop.photocompressor.IntegrationTests;

import com.amazonaws.services.s3.model.S3Object;
import devops.workshop.photocompressor.config.AwsConfig;
import devops.workshop.photocompressor.services.ImageServiceS3;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "image.size=400",
        "image.resized.folder=${PWD}/src/test/resources/resizedimages",
        "aws.access.key=${AWS_ACCESS_KEY}",
        "aws.secret.key=${AWS_SECRET_KEY}",
        "aws.region=us-west-2",
        "aws.s3.bucket.name=hajali-bucket-for-devops-integration-tests"
})
@ContextConfiguration(classes= {AwsConfig.class, ImageServiceS3.class}, loader= AnnotationConfigContextLoader.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageServiceS3Tests {
    @Autowired
    ImageServiceS3 imageServiceS3;

    private String testBucket = "hajali-bucket-for-devops-integration-tests";

    private  String resourcePath = "./src/test/resources/TestData";

    private String uploadedFileName = "test_image.jpg";

    @Test
    public void uploadFileToS3TestCase() throws IOException {
        // Given a Buffered Image
        BufferedImage bufferedImage = ImageIO.read(Paths.get(resourcePath + "/test_image.jpg").toFile());

        // When running uploadImage
        File file = imageServiceS3.uploadImage(uploadedFileName, bufferedImage);

        // Expect the object to be loaded
        S3Object o = imageServiceS3.getS3().getObject(testBucket, uploadedFileName);
        int oneByteOfTheObject = o.getObjectContent().read(new byte[1]);
        Assert.assertNotEquals("Object was not uploaded", -1, oneByteOfTheObject);
    }

    @After
    public void cleanUp() {
        // Remove object from S3
        imageServiceS3.getS3().deleteObject(testBucket, uploadedFileName);
    }
}
