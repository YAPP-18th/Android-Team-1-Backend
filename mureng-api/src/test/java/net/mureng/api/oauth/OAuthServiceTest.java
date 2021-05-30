package net.mureng.api.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.mureng.api.core.jwt.dto.TokenDto;
import net.mureng.api.core.oauth2.dto.OAuth2Profile;
import net.mureng.api.core.oauth2.service.OAuth2Service;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("개발용 임시 테스트")
public class OAuthServiceTest {

    @Autowired
    private OAuth2Service oAuth2Service;

    @Test
    public void 액세스토큰으로_사용자정보_얻어오기_테스트() throws JsonProcessingException {
        String provider = "kakao";
        String accessToken = "4pZgt2Sclf59PiM0COteqs50nMlO7EpsLKhf2go9dZoAAAF5tsxLNQ";
        String refreshToken = "";
        TokenDto token = new TokenDto(accessToken, refreshToken);

        OAuth2Profile profile = oAuth2Service.getProfile(provider, token.getAccessToken());
    }

}
