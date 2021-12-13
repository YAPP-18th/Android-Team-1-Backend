package net.mureng.api.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.component.FileUploader;
import net.mureng.core.core.component.DirectoryScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyImageService {
    private final FileUploader fileUploader;
    private final DirectoryScanner directoryScanner;

    @Value("${media.base.dir.name}")
    private String mediaBaseDirName;
    private String replyImageDirName = "/reply";
    private String replyDefaultImageDirName = "/default";

    @PostConstruct
    protected void init() {
        replyImageDirName = mediaBaseDirName + replyImageDirName;
        replyDefaultImageDirName = replyImageDirName + replyDefaultImageDirName;
    }

    /**
     * Multipart File 을 저장하고, 저장 경로를 리턴한다.
     *
     * @param imageFile 요청으로 들어온 Multipart File
     * @return 웹상에서 저장된 경로
     */
    public String uploadReplyImageFile(MultipartFile imageFile) {
        return fileUploader.uploadMultiPartFile(imageFile, replyImageDirName)
                .replace(mediaBaseDirName, "");
    }

    public List<String> getReplyDefaultImageList() {
        return directoryScanner.scanFileListInDirectory(replyDefaultImageDirName).stream()
                .map(x -> x.replace(mediaBaseDirName, ""))
                .collect(Collectors.toList());
    }
}
