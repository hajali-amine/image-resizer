package devops.workshop.photocompressor.IntegrationTests;

import devops.workshop.photocompressor.config.AwsConfig;
import devops.workshop.photocompressor.services.RetrieveImageServiceS3;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "image.retrieve.folder=${PWD}/src/test/resources/retrieve",
        "aws.access.key=${AWS_ACCESS_KEY}",
        "aws.secret.key=${AWS_SECRET_KEY}",
        "aws.region=us-west-2",
        "aws.s3.bucket.name=hajali-bucket-for-devops-integration-tests"
})
@ContextConfiguration(classes= {AwsConfig.class, RetrieveImageServiceS3.class}, loader= AnnotationConfigContextLoader.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RetrieveImageServiceS3Tests {
    @Autowired
    RetrieveImageServiceS3 retrieveImageServiceS3;

    private String testBucket = "hajali-bucket-for-devops-integration-tests";

    private  String resourcePath = "./src/test/resources/TestData";

    private String uploadedFileName = "test_image.jpg";

    @Before
    public void setUp() {
        // Prepare object in S3 to be read later
        retrieveImageServiceS3.getS3().putObject(testBucket, uploadedFileName, Paths.get(resourcePath + "/test_image.jpg").toFile());
    }

    @Test
    public void uploadFileToS3TestCase() throws IOException {
        // Given nothing
        // When running retrieveImage
        byte[] retrievedImage = retrieveImageServiceS3.retrieveImage(uploadedFileName);

        // Expect the object to be retrieved
        Assert.assertTrue("Object was not retrieved", retrievedImage.length > 0);
    }

    @After
    public void cleanUp() {
        // Remove object from S3
        retrieveImageServiceS3.getS3().deleteObject(testBucket, uploadedFileName);
    }
}
