package devops.workshop.photocompressor.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import devops.workshop.photocompressor.config.AwsConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RetrieveImageServiceS3 implements IRetrieveImageService {
    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Value("${image.retrieve.folder}")
    private String retrieveFolder;

    @Override
    public byte[] retrieveImage(String image) throws IOException {
        try {
            S3Object o = AwsConfig.s3.getObject(s3BucketName, image);
            S3ObjectInputStream s3is = o.getObjectContent();
            Path path = Paths.get(retrieveFolder, image);
            FileOutputStream fos = new FileOutputStream(path.toFile());
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();

            InputStream in = new FileInputStream(path.toFile());
            return IOUtils.toByteArray(in);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
