package net.mureng.api.member.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.MemberScrap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberScrapMapperTest {

    @Autowired
    private MemberScrapMapper memberScrapMapper;

    private final TodayExpressionDto todayExpressionDto = DtoCreator.createTodayExpressionDto();
    private final MemberScrap memberScrap = EntityCreator. createMemberScrapEntity();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        TodayExpressionDto mappedDto = memberScrapMapper.toDto(memberScrap);
        assertEquals(todayExpressionDto.getExpId(), mappedDto.getExpId());
        assertEquals(todayExpressionDto.getExpression(), mappedDto.getExpression());
        assertEquals(todayExpressionDto.getMeaning(), mappedDto.getMeaning());
        assertEquals(todayExpressionDto.getExpressionExample(), mappedDto.getExpressionExample());
        assertEquals(todayExpressionDto.getExpressionExampleMeaning(), mappedDto.getExpressionExampleMeaning());
        assertTrue(mappedDto.isScrappedByRequester());
    }
}
