package net.mureng.api.member.component;

import net.mureng.api.badge.dto.BadgeDto;
import net.mureng.api.common.DtoCreator;
import net.mureng.api.member.dto.MemberAchievementDto;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class MemberAchievementMapperTest {

    @Autowired
    private MemberAchievementMapper memberAchievementMapper;

    private static final List<Badge> badges = Arrays.asList(EntityCreator.createBadgeEntity(), EntityCreator.createBadgeEntity());
    private static final List<BadgeDto> badgeDtos = Arrays.asList(DtoCreator.createBadgeDto(), DtoCreator.createBadgeDto());

    private static final Member member = EntityCreator.createMemberEntity();
    private static final MemberDto memberDto = DtoCreator.createMemberDto();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        MemberAchievementDto mappedDto = memberAchievementMapper.toDto(member, badges, member);

        assertEquals(mappedDto.getMember().getEmail(), memberDto.getEmail());
        assertEquals(mappedDto.getBadges().size(), badgeDtos.size());
        assertEquals(mappedDto.getBadges().get(0).getName(), badgeDtos.get(0).getName());
        assertTrue(mappedDto.isRequesterProfile());
    }

    @Test
    public void 엔티티에서_DTO변환_테스트_뱃지획득() {
        MemberAchievementDto mappedDto = memberAchievementMapper.toDto(member, badges, member, true);

        assertEquals(mappedDto.getMember().getEmail(), memberDto.getEmail());
        assertEquals(mappedDto.getBadges().size(), badgeDtos.size());
        assertEquals(mappedDto.getBadges().get(0).getName(), badgeDtos.get(0).getName());
        assertTrue(mappedDto.isRequesterProfile());
        assertEquals(BadgeAccomplishedServiceImpl.CelebrityMureng.id, mappedDto.getAccomplishedBadge());
    }
}
