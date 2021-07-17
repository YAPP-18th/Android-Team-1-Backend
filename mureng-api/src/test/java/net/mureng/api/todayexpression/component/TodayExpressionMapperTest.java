package net.mureng.api.todayexpression.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.TodayExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TodayExpressionMapperTest {

    @Autowired
    private TodayExpressionMapper todayExpressionMapper;

    @Autowired
    private TodayExpressionWithBadgeMapper todayExpressionWithBadgeMapper;

    private final TodayExpression todayExpression = EntityCreator.createTodayExpressionEntity();
    private final TodayExpressionDto todayExpressionDto = DtoCreator.createTodayExpressionDto();

    private final Member member = EntityCreator.createMemberEntity();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        TodayExpressionDto mappedDto = todayExpressionMapper.toDto(todayExpression, member);
        assertEquals(todayExpressionDto.getExpId(), mappedDto.getExpId());
        assertEquals(todayExpressionDto.getExpression(), mappedDto.getExpression());
        assertEquals(todayExpressionDto.getMeaning(), mappedDto.getMeaning());
        assertEquals(todayExpressionDto.getExpressionExample(), mappedDto.getExpressionExample());
        assertEquals(todayExpressionDto.getExpressionExampleMeaning(), mappedDto.getExpressionExampleMeaning());
        assertTrue(mappedDto.isScrappedByRequester());
    }

    @Test
    public void 엔티티에서_DTO변환_테스트_뱃지획득() {
        TodayExpressionDto mappedDto = todayExpressionWithBadgeMapper.toDto(todayExpression, member, true);
        assertEquals(todayExpressionDto.getExpId(), mappedDto.getExpId());
        assertEquals(todayExpressionDto.getExpression(), mappedDto.getExpression());
        assertEquals(todayExpressionDto.getMeaning(), mappedDto.getMeaning());
        assertEquals(todayExpressionDto.getExpressionExample(), mappedDto.getExpressionExample());
        assertEquals(todayExpressionDto.getExpressionExampleMeaning(), mappedDto.getExpressionExampleMeaning());
        assertTrue(mappedDto.isScrappedByRequester());
        assertEquals(BadgeAccomplishedServiceImpl.AcademicMureng.id, mappedDto.getAccomplishedBadge());
    }
}
