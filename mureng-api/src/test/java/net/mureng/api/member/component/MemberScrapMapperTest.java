package net.mureng.api.member.component;

import net.mureng.api.common.DtoCreator;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.api.member.dto.MemberScrapDto;
import net.mureng.api.todayexpression.dto.TodayExpressionDto;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.todayexpression.entity.TodayExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberScrapMapperTest {
    @Autowired
    private MemberScrapMapper memberScrapMapper;

    private static final MemberDto memberDto = DtoCreator.createMemberDto();
    private static final List<TodayExpressionDto> todayExpressionDtoList = Arrays.asList(DtoCreator.createTodayExpressionDto(), DtoCreator.createTodayExpressionDto());
    private static final Member member = EntityCreator.createMemberEntity();
    private static final List<TodayExpression> todayExpressionList = Arrays.asList(EntityCreator.createTodayExpressionEntity(), EntityCreator.createTodayExpressionEntity());

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        MemberScrapDto mappedDto = memberScrapMapper.toDto(member, todayExpressionList, member);
        assertEquals(mappedDto.getMember().getEmail(), memberDto.getEmail());
        assertEquals(mappedDto.getScrapList().get(0).getExpression(), todayExpressionDtoList.get(0).getExpression());
        assertTrue(mappedDto.getScrapList().get(0).isScrappedByRequester());
        assertTrue(mappedDto.isRequesterProfile());
    }
}
