package net.mureng.api.core.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemFileUploaderTest {
    private final FileSystemFileUploader fileUploader = new FileSystemFileUploader();
    private String filePath = "";

    @Test
    public void 파일_저장_테스트() {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        // when
        filePath = fileUploader.uploadMultiPartFile(mockMultipartFile, "./");

        // then
        File testFile = new File(filePath);
        assertTrue(testFile.exists());
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(filePath);
        if (testFile.exists())
            testFile.delete();
    }
}