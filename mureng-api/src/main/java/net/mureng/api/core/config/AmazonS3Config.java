package net.mureng.api.core.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {
    @Value("${cloud.aws.credentials.access_key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret_key}")
    private String secretKey;
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        //AmazonS3ClientBuilder를 통해 S3 Client를 가져와야 하는데, 자격증명을 해줘야 S3 Client를 가져올 수 있기 때문입니다.
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }
}
