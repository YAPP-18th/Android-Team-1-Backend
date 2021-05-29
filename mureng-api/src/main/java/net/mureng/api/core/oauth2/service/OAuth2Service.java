package net.mureng.api.core.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.exception.BadRequestException;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final ObjectMapper objectMapper;

    public OAuth2Profile getProfile(String provider, String accessToken) throws JsonProcessingException {
        JsonNode json = getJsonResponse(provider.toLowerCase(), accessToken);

        if("kakao".equals(provider.toLowerCase()))
            return setKakaoProfile(json);
        else if("google".equals(provider.toLowerCase()))
            return setGoogleProfile(json);
        else
            throw new BadRequestException("잘못된 provider 입니다.");
    }

    public JsonNode getJsonResponse(String provider, String accessToken) throws JsonProcessingException, HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                env.getProperty(String.format("spring.social.%s.url.profile", provider)),
                request,
                String.class
        );

        return objectMapper.readTree(response.getBody());
    }

    public OAuth2Profile setKakaoProfile(JsonNode jsonNode) {
        String email = jsonNode.get("kakao_account").get("email").textValue();

        return new OAuth2Profile(email);
    }

    public OAuth2Profile setGoogleProfile(JsonNode jsonNode){
        String email = jsonNode.get("email").textValue();

        return new OAuth2Profile(email);
    }
}
