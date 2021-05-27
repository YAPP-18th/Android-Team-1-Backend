package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.member.service.MemberExpressionScrapService;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.MemberScrap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberScrapControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberExpressionScrapService memberExpressionScrapService;

    private static final long EXP_ID = 1L;
    private static final long MEMBER_ID = 1L;

    @Test
    @WithMockMurengUser
    public void 오늘의표현_스크랩_테스트() throws Exception{
        MemberScrap memberScrap = EntityCreator.createMemberScrapEntity();

        given(memberExpressionScrapService.scrapTodayExpression(any(), eq(EXP_ID))).willReturn(memberScrap);

        mockMvc.perform(
                post("/api/member/scrap/{expId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.meaning").value("테스트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressionExample").value("test driven development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressionExampleMeaning").value("테스트 주도 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrappedByRequester").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 사용자_스크랩_목록_가져오기_테스트() throws Exception {
        List<MemberScrap> memberScrapList = Arrays.asList(EntityCreator.createMemberScrapEntity(), EntityCreator.createMemberScrapEntity());

        given(memberExpressionScrapService.getMemberScrap(eq(MEMBER_ID))).willReturn(memberScrapList);

        mockMvc.perform(
                get("/api/member/{memberId}/scrap", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].meaning").value("테스트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExample").value("test driven development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExampleMeaning").value("테스트 주도 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].scrappedByRequester").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.requesterProfile").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 내_스크랩_목록_가져오기_테스트() throws Exception {
        List<MemberScrap> memberScrapList = Arrays.asList(EntityCreator.createMemberScrapEntity(), EntityCreator.createMemberScrapEntity());

        given(memberExpressionScrapService.getMemberScrap(eq(MEMBER_ID))).willReturn(memberScrapList);

        mockMvc.perform(
                get("/api/member/me/scrap")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].meaning").value("테스트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExample").value("test driven development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExampleMeaning").value("테스트 주도 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].scrappedByRequester").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.requesterProfile").value(true))
                .andDo(print());
    }
}
