package net.mureng.core.core.component;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.mureng.core.core.message.ErrorMessage.NOT_DIRECTORY;

@Component
public class DirectoryScanner {

    /**
     * 특정 경로를 스캔하여 파일명의 목록을 리턴한다.
     * @param directoryPath 디렉터리 경로
     * @return 파일 경로의 목록
     * @throws IllegalArgumentException 파일 경로가 들어왔을 경우
     */
    public List<String> scanFileListInDirectory(String directoryPath) {
        File[] listFiles = new File(directoryPath).listFiles();
        if (listFiles == null) { // null 인 경우 파일이다.
            throw new IllegalArgumentException(NOT_DIRECTORY);
        }

        List<String> fileList = new ArrayList<>();
        for (File f : listFiles) {
            if (f.isDirectory()) {
                continue;
            }

            fileList.add(f.getAbsolutePath());
        }
        return fileList;
    }
}
