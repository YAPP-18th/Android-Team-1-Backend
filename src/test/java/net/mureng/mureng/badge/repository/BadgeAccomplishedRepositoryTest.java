package net.mureng.mureng.badge.repository;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import net.mureng.mureng.annotation.MurengDataTest;
import net.mureng.mureng.badge.entity.Badge;
import net.mureng.mureng.badge.entity.BadgeAccomplished;
import net.mureng.mureng.badge.entity.BadgeAccomplishedPK;
import net.mureng.mureng.badge.repository.BadgeAccomplishedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@MurengDataTest
@DatabaseSetup({
        "classpath:dbunit/entity/member.xml",
        "classpath:dbunit/entity/badge.xml",
        "classpath:dbunit/entity/badge_accomplished.xml",
})
class BadgeAccomplishedRepositoryTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    private BadgeAccomplishedRepository badgeAccomplishedRepository;

    @Test
    public void 멤버_뱃지완료_조회_테스트() {
        BadgeAccomplishedPK id = BadgeAccomplishedPK.builder()
                .badgeId(1L)
                .memberId(MEMBER_ID)
                .build();

        BadgeAccomplished badgeAccomplished = badgeAccomplishedRepository.findById(id).orElseThrow();

        assertEquals(2020, badgeAccomplished.getRegDate().getYear());
        assertEquals(10, badgeAccomplished.getRegDate().getMonthValue());
        assertEquals(14, badgeAccomplished.getRegDate().getDayOfMonth());
    }

    @Test
    public void 멤버로_뱃지완료목록_조회_테스트(){
        List<BadgeAccomplished> badgeAccomplishes = badgeAccomplishedRepository.findBadgeAccomplishedsByIdMemberId(MEMBER_ID);

        assertEquals(2, badgeAccomplishes.size());

        assertEquals(2020, badgeAccomplishes.get(0).getRegDate().getYear());
        assertEquals(10, badgeAccomplishes.get(0).getRegDate().getMonthValue());
        assertEquals(14, badgeAccomplishes.get(0).getRegDate().getDayOfMonth());
        assertEquals(1L, badgeAccomplishes.get(0).getId().getBadgeId());

        assertEquals(2020, badgeAccomplishes.get(1).getRegDate().getYear());
        assertEquals(10, badgeAccomplishes.get(1).getRegDate().getMonthValue());
        assertEquals(15, badgeAccomplishes.get(1).getRegDate().getDayOfMonth());
        assertEquals(2L, badgeAccomplishes.get(1).getId().getBadgeId());
    }

    @Test
    public void 멤버로_뱃지완료목록_내용조회_테스트(){
        List<Badge> todayExpressions = badgeAccomplishedRepository.findBadgeAccomplishedsByIdMemberId(MEMBER_ID).stream()
                .map(BadgeAccomplished::getBadge)
                .collect(Collectors.toList());

        assertEquals(2, todayExpressions.size());

        assertEquals(1L, todayExpressions.get(0).getBadgeId());
        assertEquals("테스트", todayExpressions.get(0).getName());
        assertEquals("테스트 배지", todayExpressions.get(0).getContent());

        assertEquals(2L, todayExpressions.get(1).getBadgeId());
        assertEquals("테스트2", todayExpressions.get(1).getName());
        assertEquals("테스트2 배지", todayExpressions.get(1).getContent());
    }
}