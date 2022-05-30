package devops.workshop.photocompressor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class RetrieveAndCleanServiceS3 {
    @Value("${image.retrieve.folder}")
    private String retrieveFolder;
    
    private RetrieveImageServiceS3 retrieveImageServiceS3;

    public RetrieveAndCleanServiceS3(RetrieveImageServiceS3 retrieveImageServiceS3) {
        this.retrieveImageServiceS3 = retrieveImageServiceS3;
    }
    
    public byte[] retrieveAndClean(String image) throws IOException {
        byte[] s3Image = retrieveImageServiceS3.retrieveImage(image);
        Paths.get(retrieveFolder, image).toFile().delete();
        return s3Image;
    }
}
