//package devops.workshop.photocompressor.UnitTests;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.S3Object;
//import devops.workshop.photocompressor.services.RetrieveImageServiceS3;
//import org.junit.*;
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
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static org.mockito.ArgumentMatchers.anyString;
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = RetrieveImageServiceS3.class)
//@TestPropertySource(properties = {"image.retrieve.folder=src/test/resources/retrieve"})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class RetrieveImageServiceS3Tests {
//    @Mock
//    AmazonS3 s3;
//
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();
//
//    @Autowired
//    @Mock
//    private RetrieveImageServiceS3 retrieveImageServiceS3;
//
//    private  String resourcePath = "./src/test/resources/TestData";
//
//    private String imagePath = "src/test/resources/retrieve/";
//
//    private String uploadedFileName = "test_image.jpg";
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(retrieveImageServiceS3, // inject into this object
//                "retrieveFolder", // assign to this field
//                System.getProperty("user.dir") + "src/test/resources/retrieve"); // object to be injected
//    }
//
//    @Test
//    @Order(1)
//    @I
//    public void uploadFileFromMultipartFileTestCase() throws IOException {
//        S3Object object = new S3Object();
//        object.setObjectContent(new FileInputStream(Paths.get(resourcePath + "/" + uploadedFileName).toFile()));
//        Mockito.when(retrieveImageServiceS3.getS3()).thenReturn(s3);
//        Mockito.when(s3.getObject(anyString(), anyString())).thenReturn(object); // Mock call to S3
//        Mockito.when(retrieveImageServiceS3.retrieveImage(uploadedFileName)).thenCallRealMethod(); // Call real method
//
//        // When running retrieveImage
//        byte[] file = retrieveImageServiceS3.retrieveImage(uploadedFileName);
//
//        // Expect an image same to the one mocked
//        byte[] realFile = Files.readAllBytes(Paths.get(imagePath + uploadedFileName));
//        Assert.assertEquals(
//                "The image uploaded is Null.",
//                file.length,
//                realFile.length
//        );
//    }
//}
