package net.mureng.mureng.member.web;

import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberControllerTest extends AbstractControllerTest {
    private final String newMemberJsonString = "{\"attendanceCount\": 0,\n" +
                                                "  \"dailyEndTime\": \"2020-10-14\",\n" +
                                                "  \"email\": \"example@email.com\",\n" +
                                                "  \"identifier\": \"user-identifier\",\n" +
                                                "  \"image\": \"image-path\",\n" +
                                                "  \"lastAttendanceDate\": \"string\",\n" +
                                                "  \"memberId\": 0,\n" +
                                                "  \"murengCount\": 0,\n" +
                                                "  \"nickname\": \"string\",\n" +
                                                "  \"pushActive\": true\n" +
                                                "}";

    @Test
    public void 사용자_회원가입_테스트() {

    }
}