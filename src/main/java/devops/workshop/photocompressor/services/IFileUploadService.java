package devops.workshop.photocompressor.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IFileUploadService {
    File upload(MultipartFile imageFile) throws IOException;
    void clean(File sourceFile);
}
