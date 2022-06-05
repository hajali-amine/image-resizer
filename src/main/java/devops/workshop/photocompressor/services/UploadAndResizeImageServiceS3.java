package devops.workshop.photocompressor.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class UploadAndResizeImageServiceS3 {
    private IFileUploadService fileUploadService;
    private IImageService imageServiceS3;

    public UploadAndResizeImageServiceS3(IFileUploadService fileUploadService, IImageService imageServiceS3) {
        this.fileUploadService = fileUploadService;
        this.imageServiceS3 = imageServiceS3;
    }

    public String uploadAndResize(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()) {
            return "wtf bro";
        }

        File file = fileUploadService.upload(imageFile);
        if(file == null) {
            return "Upload failed";

        }
        BufferedImage resizeResult =  imageServiceS3.resizeImage(file);

        File newFile = imageServiceS3.uploadImage(file.getName(), resizeResult);

        imageServiceS3.clean(newFile);

        fileUploadService.clean(file);

        return "jawek behi";
    }
}
