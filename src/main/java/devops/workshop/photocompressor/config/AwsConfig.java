package devops.workshop.photocompressor.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AwsConfig {
    public static AmazonS3 s3;

    @Value("${aws.access.key}")
    private String accessKeyId;

    @Value("${aws.secret.key}")
    private String accessKeySecret;

    @Value("${aws.region}")
    private String s3RegionName;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        // Get Amazon S3 client and return the S3 client object
        s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3RegionName)
                .build();
        return s3;
    }

    @Bean
    public boolean createBucket(){
        if (getAmazonS3Client().doesBucketExistV2(s3BucketName)) {
            System.out.format("Bucket %s already exists.\n", s3BucketName);
            return true;
        } else {
            try {
                Bucket b = getAmazonS3Client().createBucket(s3BucketName);
                return true;
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
                return false;
            }
        }
    }
}