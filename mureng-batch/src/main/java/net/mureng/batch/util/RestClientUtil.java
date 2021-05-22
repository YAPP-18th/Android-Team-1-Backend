package net.mureng.batch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestClientUtil {
    /* 인스턴스화 방지 */
    private RestClientUtil() { }

    //private static final HttpComponentsClientHttpRequestFactory HTTP_CLIENT_FACTORY = new HttpComponentsClientHttpRequestFactory();

    public static final RestTemplate REST_TEMPLATE = new RestTemplate();
//    static {
//        HTTP_CLIENT_FACTORY.setReadTimeout(5000);
//        HTTP_CLIENT_FACTORY.setConnectTimeout(3000);
//    }

    public static <T> T postForm(String url, MultiValueMap<?, ?> parameters, Class<T> type) {
        return REST_TEMPLATE.postForObject(url, parameters, type);
    }

    public static <T> T get(String url, Class<T> type) {
        return REST_TEMPLATE.getForObject(url, type);
    }

    public static <T> T exchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> type) {
        ResponseEntity<T> response = REST_TEMPLATE.exchange(url, method, entity, type);
        return response.getBody();
    }
}
