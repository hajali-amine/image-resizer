//package devops.workshop.photocompressor.UnitTests;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectResult;
//import devops.workshop.photocompressor.services.ImageServiceS3;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static org.mockito.ArgumentMatchers.anyString;
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = ImageServiceS3.class)
//@TestPropertySource(properties = {"image.resized.folder=${PWD}/src/test/resources/resizedimages", "image.size=400"})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ImageServiceS3Tests {
//    @Mock
//    AmazonS3 s3;
//
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();
//
//    @Autowired
//    @Mock
//    private ImageServiceS3 imageServiceS3;
//
//    private  String resourcePath = "./src/test/resources/TestData";
//
//    private String imagePath = "src/test/resources/resizedImages/";
//
//    private String uploadedFileName = "test_image.jpg";
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(imageServiceS3, // inject into this object
//                "resizedImageFolder", // assign to this field
//                System.getProperty("user.dir") + "src/test/resources/resizedImages"); // object to be injected
//    }
//
////    @Test
////    @Order(1)
////    public void uploadFileFromMultipartFileTestCase() throws IOException {
////        // TODO ADD METHOD GET S3 IN CLASS
////        Mockito.when(s3.putObject(anyString(), anyString(), anyString())).thenReturn(new PutObjectResult());
////        Mockito.when(imageServiceS3.getS3()).thenReturn(s3);
////        // Given a Buffered Image
////        BufferedImage bufferedImage = ImageIO.read(Paths.get(resourcePath + "/test_image.jpg").toFile());
////        Mockito.when(imageServiceS3.uploadImage(uploadedFileName, bufferedImage)).thenCallRealMethod();
////
////        // When running uploadImage
////        File file = imageServiceS3.uploadImage(resourcePath + "/" + uploadedFileName, bufferedImage);
////
////        // Expect an image in the images folder
////        byte[] fileResized = Files.readAllBytes(Paths.get(imagePath + uploadedFileName));
////        Assert.assertNotEquals(
////                "The image uploaded is Null.",
////                0,
////                fileResized.length
////        );
////    }
//
////    @Test
////    @Order(2)
////    public void cleanUploadedFileTestCase() throws IOException {
////        // Given the file uploaded by the previous test
////        File file = Paths.get(imagePath + uploadedFileName).toFile();
////
////        // When running clean
////        fileUploadServiceS3.clean(file);
////
////        // Expect an image in the images folder
////        Assert.assertThrows(
////                "The image uploaded was not deleted.",
////                NoSuchFileException.class,
////                () -> {Files.readAllBytes(Paths.get(imagePath + uploadedFileName));}
////        );
////    }
//}
