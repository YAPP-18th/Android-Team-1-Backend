package net.mureng.api.core.component;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//@Component
@Deprecated
public class FileSystemFileUploader implements FileUploader {
    /**
     * Multipart 요청 파일을 저장하고 저장된 경로를 반환한다.
     *
     * @param mFile Controller 에서 Http Multipart 요청으로 들어온 File
     * @param filePath 파일이 저장될 경로
     * @return 실제로 파일이 저장되었을 때, 리턴되어지는 경로
     *
     * @throws IllegalStateException 파일 저장 실패시 원인과 함께 throw
     */
    @Override
    public String uploadMultiPartFile(MultipartFile mFile, String filePath) {
        try {
            makeAbsoluteDirectory(filePath);
            String generatedFileName = generateFileName(mFile);
            String completePath = filePath + "/" + generatedFileName;
            File saveFile = new File(completePath);
            mFile.transferTo(saveFile);
            return saveFile.getAbsolutePath();
        } catch (IllegalStateException | IOException e) {
            String msg = "파일 저장 중 에러 발생";
            throw new IllegalStateException(msg, e);
        }
    }

    private void makeAbsoluteDirectory(String dirPath) {
        File directory = new File(dirPath);
        if (directory.exists() && directory.isDirectory()) {
            return;
        }

        if (directory.exists() && ! directory.isDirectory()) {
            String msg = "경로가 이미 존재하며, 파일입니다.";
            throw new IllegalStateException(msg);
        }

        boolean result = directory.mkdirs();
        if (! result) {
            String msg = "디렉토리 생성 중 에러 발생";
            throw new IllegalStateException(msg);
        }
    }

    // 확장자 추출
    private String getExtension(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return originalFileName.substring(lastIndex);
    }

    private String generateFileName(MultipartFile mFile) {
        String currentTimeStamp = String.valueOf(System.currentTimeMillis());
        String originalFileName = mFile.getOriginalFilename() == null ? "" : mFile.getOriginalFilename();
        String extension = getExtension(originalFileName);
        return currentTimeStamp + extension;
    }
}
