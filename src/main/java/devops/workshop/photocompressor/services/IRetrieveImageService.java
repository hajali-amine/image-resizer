package devops.workshop.photocompressor.services;


import java.io.IOException;

public interface IRetrieveImageService {
    byte[] retrieveImage(String image) throws IOException;
}
