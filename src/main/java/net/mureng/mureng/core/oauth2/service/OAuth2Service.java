package net.mureng.mureng.core.oauth2.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.core.oauth2.dto.OAuth2Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    private final RestTemplate restTemplate;
    private final Environment env;

    public OAuth2Profile getProfile(String provider, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    env.getProperty(String.format("spring.social.%s.url.profile", provider)),
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(response.getBody(), OAuth2Profile.class);
            }
        } catch (Exception e) {
            throw new BadRequestException("HTTP 요청 에러입니다.");
        }
        throw new BadRequestException("OAuth 존재하지 않는 사용자입니다.");
    }
}
