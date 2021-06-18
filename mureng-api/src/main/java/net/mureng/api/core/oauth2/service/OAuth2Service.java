package net.mureng.api.core.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.jwt.dto.TokenProvider;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.MurengException;
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

    public OAuth2Profile getProfile(TokenDto.Provider token) {
        JsonNode json = getJsonResponse(token);

        if(TokenProvider.KAKAO == token.getProviderName())
            return setKakaoProfile(json);
        else if(TokenProvider.GOOGLE == token.getProviderName())
            return setGoogleProfile(json);
        else
            throw new BadRequestException("잘못된 provider 입니다.");
    }

    public JsonNode getJsonResponse(TokenDto.Provider token) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token.getProviderAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        String url = env.getProperty(String.format("spring.social.%s.url.profile",token.getProviderName()));

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                request,
                String.class
        );

        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new MurengException("Provider 응답 호출 중 예외 발생", e);
        }

    }

    public OAuth2Profile setKakaoProfile(JsonNode jsonNode) {
        long identifier = jsonNode.get("id").asLong();

        return new OAuth2Profile("kakao_" + identifier);
    }

    public OAuth2Profile setGoogleProfile(JsonNode jsonNode){
        long identifier = jsonNode.get("localId").asLong();

        return new OAuth2Profile("google_" + identifier);
    }
}
