package devops.workshop.photocompressor.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class UploadAndResizeImageServiceS3 {
    private FileUploadService fileUploadServiceS3;
    private ImageService imageServiceS3;

    public UploadAndResizeImageServiceS3(FileUploadService fileUploadServiceS3, ImageService imageServiceS3) {
        this.fileUploadServiceS3 = fileUploadServiceS3;
        this.imageServiceS3 = imageServiceS3;
    }

    public String uploadAndResize(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()) {
            return "wtf bro";
        }

        File file = fileUploadServiceS3.upload(imageFile);
        if(file == null) {
            return "Upload failed";

        }
        BufferedImage resizeResult =  imageServiceS3.resizeImage(file);

        File newFile = imageServiceS3.uploadImage(file.getName(), resizeResult);

        fileUploadServiceS3.clean(file);

        return "jawek behi";
    }
}
