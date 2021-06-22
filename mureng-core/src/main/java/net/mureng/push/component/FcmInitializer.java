package net.mureng.push.component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FcmInitializer {
    @Value("${firebase.service.key}")
    private String firebaseServiceKey;

    @PostConstruct
    public void initialize()  {
        if (! StringUtils.hasText(firebaseServiceKey)) {
            return;
        }

        if (isBase64Encoded(firebaseServiceKey)) {
            firebaseServiceKey = new String(Base64.getDecoder().decode(firebaseServiceKey));
        }

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(firebaseServiceKey.getBytes())))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isBase64Encoded(String key) {
        Pattern pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        Matcher matcher = pattern.matcher(key);
        return matcher.find();
    }
}
