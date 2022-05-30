package devops.workshop.photocompressor.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceS3 implements IFileUploadService {
    @Value("${image.source.folder}")
    private String imageFolder;

    @Override
    public File upload(MultipartFile imageFile) throws IOException {
        Path path = Paths.get(imageFolder, imageFile.getOriginalFilename());
        Files.write(path, imageFile.getBytes());
        return path.toFile();
    }

    @Override
    public void clean(File sourceFile) {
        sourceFile.delete();

    }
}
