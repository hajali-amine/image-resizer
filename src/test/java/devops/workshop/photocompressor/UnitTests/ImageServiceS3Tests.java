package devops.workshop.photocompressor.UnitTests;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import devops.workshop.photocompressor.services.ImageServiceS3;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
@TestPropertySource(properties = {"image.resized.folder=${PWD}/src/test/resources/resizedimages", "image.size=400"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageServiceS3Tests {
    @Mock
    AmazonS3 s3;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Autowired
    @Mock
    private ImageServiceS3 imageServiceS3;

    private  String resourcePath = "./src/test/resources/TestData";

    private String imagePath = "src/test/resources/resizedimages/";

    private String uploadedFileName = "test_image.jpg";

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(imageServiceS3, // inject into this object
                "resizedImageFolder", // assign to this field
                "src/test/resources/resizedimages"); // object to be injected
        ReflectionTestUtils.setField(imageServiceS3,
                "imageSize",
                400);
    }

    @Test
    @Order(1)
    public void resizeImageTestCase() throws IOException {
        // Given an image file
        File image = Paths.get(resourcePath + "/test_image.jpg").toFile();
        Mockito.when(imageServiceS3.resizeImage(image)).thenCallRealMethod();

        // When running uploadImage
        BufferedImage resizedImage = imageServiceS3.resizeImage(image);

        // Expect an image in the images folder
        BufferedImage originalImage = ImageIO.read(Paths.get(resourcePath + "/test_image.jpg").toFile());
        Assert.assertTrue(
                "The image uploaded is Null.",
                resizedImage.getHeight() * resizedImage.getWidth() < originalImage.getHeight() * originalImage.getWidth()
        );
    }

    @Test
    @Order(2)
    public void uploadFileToS3TestCase() throws IOException {
        Mockito.when(s3.putObject(anyString(), anyString(), anyString())).thenReturn(new PutObjectResult()); // Mock call to S3
        Mockito.when(imageServiceS3.getS3()).thenReturn(s3);
        // Given a Buffered Image
        BufferedImage bufferedImage = ImageIO.read(Paths.get(resourcePath + "/test_image.jpg").toFile());
        Mockito.when(imageServiceS3.uploadImage(uploadedFileName, bufferedImage)).thenCallRealMethod(); // Call real method

        // When running uploadImage
        File file = imageServiceS3.uploadImage(uploadedFileName, bufferedImage);

        // Expect an image in the images folder
        byte[] fileResized = Files.readAllBytes(Paths.get(imagePath + uploadedFileName));
        Assert.assertNotEquals(
                "The image uploaded is Null.",
                0,
                fileResized.length
        );
    }

    @Test
    @Order(3)
    public void cleanUploadedFileTestCase() throws IOException {
        // Given the file uploaded by the previous test
        File file = Paths.get(imagePath + uploadedFileName).toFile();
        Mockito.doCallRealMethod().when(imageServiceS3).clean(file); // Call real method

        // When running clean
        imageServiceS3.clean(file);

        // Expect no image in the folder
        Assert.assertThrows(
                "The image uploaded was not deleted.",
                NoSuchFileException.class,
                () -> {Files.readAllBytes(Paths.get(imagePath + uploadedFileName));}
        );
    }
}
