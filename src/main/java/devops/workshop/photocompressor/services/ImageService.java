package devops.workshop.photocompressor.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageService {
    BufferedImage resizeImage(File sourceFile) throws IOException;
    File uploadImage(String fileName, BufferedImage resizedImage) throws IOException;
    void clean(File sourceFile);
}
