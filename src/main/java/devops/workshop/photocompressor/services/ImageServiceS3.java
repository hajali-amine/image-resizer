package devops.workshop.photocompressor.services;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceS3 implements ImageService {
    @Value("${image.resized.folder}")
    private String resizedImageFolder;

    @Value("${image.size}")
    private Integer imageSize;

    @Override
    public BufferedImage resizeImage(File sourceFile) throws IOException {
        // Read image as a buffered image
        BufferedImage bufferedImage = ImageIO.read(sourceFile);
        // Here is where the compression is done
        BufferedImage outputImage = Scalr.resize(bufferedImage, imageSize);
        return outputImage;
    }

    @Override
    public File uploadImage(String fileName, BufferedImage resizedImage) throws IOException {
        String newFileName = FilenameUtils.getBaseName(fileName)
                + "."
                + FilenameUtils.getExtension(fileName);
        Path path = Paths.get(resizedImageFolder, newFileName);
        File newImageFile = path.toFile();
        ImageIO.write(resizedImage, "jpg", newImageFile);
        resizedImage.flush();
        return newImageFile;
    }

    @Override
    public void clean(File sourceFile) {

    }
}
