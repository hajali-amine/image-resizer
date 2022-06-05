//package devops.workshop.photocompressor.IntegrationTests;
//
//import devops.workshop.photocompressor.services.ImageServiceS3;
//import org.junit.Test;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//
//
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = ImageServiceS3.class)
//@TestPropertySource(properties = {
//        "image.size=400",
//        "image.resized.folder=${PWD}/src/test/resources/resizedimages",
//        "aws.access.key=${AWS_ACCESS_KEY}",
//        "aws.secret.key=${AWS_SECRET_KEY}",
//        "aws.region=us-west-2",
//        "aws.s3.bucket.name=hajali-bucket-for-devops-integration-tests"
//})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ImageServiceS3Tests {
//    @Autowired
//    ImageServiceS3 imageServiceS3;
//
//    private  String resourcePath = "./src/test/resources/TestData";
//    private String uploadedFileName = "test_image.jpg";
//
//    @Test
//    public void uploadFileFromMultipartFileTestCase() throws IOException {
//        // Given a Buffered Image
//        BufferedImage bufferedImage = ImageIO.read(Paths.get(resourcePath + "/test_image.jpg").toFile());
//
//        // When running uploadImage
//        File file = imageServiceS3.uploadImage(uploadedFileName, bufferedImage);
//
//        // Expect an image in the images folder
////        byte[] fileResized = Files.readAllBytes(Paths.get(imagePath + uploadedFileName));
////        Assert.assertNotEquals(
////                "The image uploaded is Null.",
////                0,
////                fileResized.length
////        );
//    }
//}
