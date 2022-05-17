package devops.workshop.photocompressor.controllers;

import devops.workshop.photocompressor.services.UploadAndResizeImageServiceS3;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {
    private UploadAndResizeImageServiceS3 uploadAndResizeImageServiceS3;

    public ImageController(UploadAndResizeImageServiceS3 uploadAndResizeImageServiceS3) {
        this.uploadAndResizeImageServiceS3 = uploadAndResizeImageServiceS3;
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile) throws IOException {
        return uploadAndResizeImageServiceS3.uploadAndResize(imageFile);
    }

}
