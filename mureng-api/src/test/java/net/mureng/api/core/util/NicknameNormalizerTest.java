package net.mureng.api.core.util;

import net.mureng.api.member.dto.MemberDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NicknameNormalizerTest {
    @Test
    void 닉네임_정규화_테스트() {
        assertEquals("최대여섯글자", NicknameNormalizer.normalize("최대여섯글자"));
        assertEquals("최대여섯글자", NicknameNormalizer.normalize("최대여섯글자초과"));
        assertEquals("MaximumNickn", NicknameNormalizer.normalize("MaximumNickn"));
        assertEquals("MaximumNickn", NicknameNormalizer.normalize("MaximumNickname"));
        assertEquals("최대여섯Nick", NicknameNormalizer.normalize("최대여섯Nickname"));
        assertEquals("최대여섯Na12", NicknameNormalizer.normalize("최대여섯Na123456"));
        assertEquals("최N대i여c섯k", NicknameNormalizer.normalize("최N대i여c섯k글n자ame"));
    }
}