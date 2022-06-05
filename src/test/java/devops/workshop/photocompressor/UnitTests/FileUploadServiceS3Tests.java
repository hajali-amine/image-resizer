package devops.workshop.photocompressor.UnitTests;

import devops.workshop.photocompressor.services.FileUploadServiceS3;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = FileUploadServiceS3.class)
@TestPropertySource(properties = {"image.source.folder=src/test/resources/images"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileUploadServiceS3Tests {
    @Autowired
    private FileUploadServiceS3 fileUploadServiceS3;

    private  String resourcePath = "./src/test/resources/TestData";

    private String imagePath = "src/test/resources/images/";

    private String uploadedFileName = "test_image.jpg";

    public MultipartFile getMultipartFile() {
        String name = uploadedFileName;
        String originalFileName = "test_image.jpg";
        String contentType = "image/jpg";
        byte[] content = null;
        try {
            content = Files.readAllBytes(Paths.get(resourcePath + "/" + originalFileName));
        } catch (final IOException e) {
        }
        return new MockMultipartFile(name, originalFileName, contentType, content);
    }

    @Test
    @Order(1)
    public void uploadFileFromMultipartFileTestCase() throws IOException {
        // Given a Multipart file
        MultipartFile multipartFile = getMultipartFile();

        // When running upload
        fileUploadServiceS3.upload(multipartFile);

        // Expect an image in the images folder
        byte[] file = Files.readAllBytes(Paths.get(imagePath + multipartFile.getName()));
        Assert.assertNotEquals(
                "The image uploaded is Null.",
                0,
                file.length
        );
    }

    @Test
    @Order(2)
    public void cleanUploadedFileTestCase() throws IOException {
        // Given the file uploaded by the previous test
        File file = Paths.get(imagePath + uploadedFileName).toFile();

        // When running clean
        fileUploadServiceS3.clean(file);

        // Expect no image in the folder
        Assert.assertThrows(
                "The image uploaded was not deleted.",
                NoSuchFileException.class,
                () -> {Files.readAllBytes(Paths.get(imagePath + uploadedFileName));}
                );
    }
}
