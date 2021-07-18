package net.mureng.api.core.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 닉네임 정규화 도구 <br>
 * 최대 12자로 규정하되, 한글의 경우 2자로 계산한다. <br>
 * 그 이상은 잘라낸다.
 */
public class NicknameNormalizer {
    private static final int MAXIMUM_LENGTH = 12;

    private NicknameNormalizer() { }

    public static String normalize(@Nullable String nickname) {
        if (! StringUtils.hasText(nickname)) {
            return nickname;
        }

        int remainLength = MAXIMUM_LENGTH;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nickname.length(); i++) {
            char ch = nickname.charAt(i);
            if (isKorean(ch)) {
                if (remainLength <= 1) {
                    return sb.toString();
                }
                sb.append(ch);
                remainLength -= 2;

            } else {
                sb.append(ch);
                remainLength -= 1;
                if (remainLength <= 0) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    private static boolean isKorean(char value) {
        return ( 0xAC00 <= value && value <= 0xD7A3 ) || ( 0x3131 <= value && value <= 0x318E );
    }
}
