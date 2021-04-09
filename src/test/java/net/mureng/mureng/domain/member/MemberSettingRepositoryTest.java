package net.mureng.mureng.domain.member;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

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
        Member member = memberSetting.getMember();

        assertThat(memberSetting.getMemberId(), is(equalTo(1L)));
        assertThat(member.getMemberId(), is(equalTo(1L)));
        assertThat(member.getIdentifier(), is(equalTo("123")));
    }
}