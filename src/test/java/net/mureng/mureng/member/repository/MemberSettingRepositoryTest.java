package net.mureng.mureng.member.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberSetting;
import net.mureng.mureng.member.repository.MemberSettingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@MurengDataTest
class MemberSettingRepositoryTest {

    @Autowired
    private MemberSettingRepository memberSettingRepository;

    @Test
    @DatabaseSetup({
            "classpath:dbunit/entity/member.xml",
            "classpath:dbunit/entity/member_setting.xml"
    })
    public void 회원_설정_조회_연관_테스트() {
        MemberSetting memberSetting = memberSettingRepository.findById(1L).orElseThrow();

        assertEquals(1L, (long)memberSetting.getMemberId());
        assertEquals(LocalTime.of(17,11,9), memberSetting.getDailyEndTime());
        assertTrue(memberSetting.isPushActive());
    }
}