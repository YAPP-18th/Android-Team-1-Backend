package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.component.FileUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class MemberImageService {
    private final FileUploader fileUploader;

    @Value("${media.base.dir.name}")
    private String mediaBaseDirName;
    private String memberImageDirName = "/member";

    @PostConstruct
    protected void init() {
        memberImageDirName = mediaBaseDirName + memberImageDirName;
    }

    /**
     * Multipart File 을 저장하고, 저장 경로를 리턴한다.
     *
     * @param imageFile 요청으로 들어온 Multipart File
     * @return 웹상에서 저장된 경로
     */
    public String uploadMemberImageFile(MultipartFile imageFile) {
        return fileUploader.uploadMultiPartFile(imageFile, memberImageDirName)
                .replace(mediaBaseDirName, "");
    }
}
