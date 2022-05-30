package devops.workshop.photocompressor.controllers;

import devops.workshop.photocompressor.services.RetrieveAndCleanServiceS3;
import devops.workshop.photocompressor.services.RetrieveImageServiceS3;
import devops.workshop.photocompressor.services.UploadAndResizeImageServiceS3;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {
    private UploadAndResizeImageServiceS3 uploadAndResizeImageServiceS3;
    private RetrieveAndCleanServiceS3 retrieveAndCleanServiceS3;

    public ImageController(UploadAndResizeImageServiceS3 uploadAndResizeImageServiceS3, RetrieveAndCleanServiceS3 retrieveAndCleanServiceS3) {
        this.uploadAndResizeImageServiceS3 = uploadAndResizeImageServiceS3;
        this.retrieveAndCleanServiceS3 = retrieveAndCleanServiceS3;
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile) throws IOException {
        return uploadAndResizeImageServiceS3.uploadAndResize(imageFile);
    }

    @GetMapping(value = "/getImage/{image}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("image") String image) throws IOException {
        return retrieveAndCleanServiceS3.retrieveAndClean(image);
    }
}
