package net.mureng.api.todayexpression.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.api.todayexpression.dto.UsefulExpressionDto;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsefulExpressionMapperTest {

    @Autowired
    private TodayExpressionMapper todayExpressionMapper;

    private final UsefulExpression usefulExpression = EntityCreator.createUsefulExpressionEntity();
    private final UsefulExpressionDto usefulExpressionDto = DtoCreator.createTodayExpressionDto();

    private final Member member = EntityCreator.createMemberEntity();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        UsefulExpressionDto mappedDto = todayExpressionMapper.toDto(usefulExpression, member);
        assertEquals(usefulExpressionDto.getExpId(), mappedDto.getExpId());
        assertEquals(usefulExpressionDto.getExpression(), mappedDto.getExpression());
        assertEquals(usefulExpressionDto.getMeaning(), mappedDto.getMeaning());
        assertEquals(usefulExpressionDto.getExpressionExample(), mappedDto.getExpressionExample());
        assertEquals(usefulExpressionDto.getExpressionExampleMeaning(), mappedDto.getExpressionExampleMeaning());
        assertTrue(mappedDto.isScrappedByRequester());
    }

}
