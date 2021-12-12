package net.mureng.api.core.component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class AmazonS3ClientTest {
    @Autowired
    private AmazonS3 s3Client;

    private String testFileName = "hello.txt";
    private MultipartFile multipartFile = new MockMultipartFile(
            "image",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
    );

    private String bucket = "mureng";

    @Test
    public void S3_파일_업로드_테스트() throws IOException {
        ObjectMetadata objMeta = buildObjectMetadata(multipartFile);

        byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

//        String uuid = UUID.randomUUID().toString();
//        fileName = fileName+uuid;

        PutObjectRequest putObjReq = new PutObjectRequest(bucket, testFileName, byteArrayIs, objMeta);
        //업로드를 하기 위해 사용되는 함수입니다.
        s3Client.putObject(putObjReq.withCannedAcl(CannedAccessControlList.PublicRead));
        //외부에 공개할 이미지이므로, 해당 파일에 public read 권한을 추가합니다.
        //업로드를 한 후, 해당 URL을 DB에 저장할 수 있도록 컨트롤러로 URL을 반환합니다.
//        System.out.println(domainUri + testFileName);
    }

    private ObjectMetadata buildObjectMetadata(MultipartFile file) throws IOException {
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(Mimetypes.getInstance().getMimetype(file.getName()));
        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objMeta.setContentLength(bytes.length);
        return objMeta;
    }

    @AfterEach
    public void tearDown() {
        s3Client.deleteObject(bucket,testFileName);
    }
}