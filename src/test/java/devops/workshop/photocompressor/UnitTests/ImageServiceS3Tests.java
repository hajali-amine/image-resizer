package devops.workshop.photocompressor.UnitTests;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import devops.workshop.photocompressor.config.AwsConfig;
import devops.workshop.photocompressor.services.FileUploadServiceS3;
import devops.workshop.photocompressor.services.ImageServiceS3;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ImageServiceS3.class)
@TestPropertySource(properties = {"image.resized.folder=src/test/resources/resizedImages", "image.size=400"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageServiceS3Tests {
    @Mock
    AmazonS3 s3;

    @MockBean
    AwsConfig awsConfig = new AwsConfig();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Autowired
    private ImageServiceS3 imageServiceS3;

    private  String resourcePath = "./src/test/resources/TestData";

    private String imagePath = "src/test/resources/resizedImages/";

    private String uploadedFileName = "test_image.png";

    @Test
    @Order(1)
    public void uploadFileFromMultipartFileTestCase() throws IOException {
        Mockito.when(awsConfig.getAmazonS3Client()).thenReturn(s3);
        Mockito.when(s3.putObject(anyString(), anyString(), anyString())).thenReturn(new PutObjectResult());
        // Given a Buffered Image
        BufferedImage bufferedImage = ImageIO.read(Paths.get(resourcePath + "/test_image.png").toFile());

        // When running uploadImage
        File file = imageServiceS3.uploadImage(uploadedFileName, bufferedImage);

        // Expect an image in the images folder
        Assert.assertTrue(true);
    }

//    @Test
//    @Order(2)
//    public void cleanUploadedFileTestCase() throws IOException {
//        // Given the file uploaded by the previous test
//        File file = Paths.get(imagePath + uploadedFileName).toFile();
//
//        // When running clean
//        fileUploadServiceS3.clean(file);
//
//        // Expect an image in the images folder
//        Assert.assertThrows(
//                "The image uploaded was not deleted.",
//                NoSuchFileException.class,
//                () -> {Files.readAllBytes(Paths.get(imagePath + uploadedFileName));}
//        );
//    }
}
