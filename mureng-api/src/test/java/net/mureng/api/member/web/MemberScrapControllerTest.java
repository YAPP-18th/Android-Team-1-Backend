package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.member.service.MemberExpressionScrapService;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.entity.MemberScrap;
import net.mureng.core.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberScrapControllerTest extends AbstractControllerTest {

    @MockBean
    private MemberExpressionScrapService memberExpressionScrapService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BadgeAccomplishedService badgeAccomplishedService;

    private static final long EXP_ID = 1L;
    private static final long MEMBER_ID = 1L;

    private static final Member member = EntityCreator.createMemberEntity();
    private static final List<MemberScrap> scrapList = Arrays.asList(EntityCreator.createMemberScrapEntity(), EntityCreator.createMemberScrapEntity());

    @Test
    @WithMockMurengUser
    public void 오늘의표현_스크랩_테스트() throws Exception{
        MemberScrap memberScrap = EntityCreator.createMemberScrapEntity();

        given(memberExpressionScrapService.scrapTodayExpression(any(), eq(EXP_ID))).willReturn(memberScrap);
        given(badgeAccomplishedService.createAcademicMureng(any())).willReturn(true);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accomplishedBadge").value(BadgeAccomplishedServiceImpl.AcademicMureng.id))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 사용자_스크랩_목록_가져오기_테스트() throws Exception {

        given(memberExpressionScrapService.getMemberScrap(eq(MEMBER_ID))).willReturn(scrapList);
        given(memberService.findById(eq(MEMBER_ID))).willReturn(member);

        mockMvc.perform(
                get("/api/member/{memberId}/scrap", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.memberId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrapList[0].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requesterProfile").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 내_스크랩_목록_가져오기_테스트() throws Exception {
        given( memberExpressionScrapService.getMemberScrap(eq(MEMBER_ID))).willReturn(scrapList);
        given(memberService.findById(eq(MEMBER_ID))).willReturn(member);

        mockMvc.perform(
                get("/api/member/me/scrap")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.memberId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrapList[0].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requesterProfile").value(true))
                .andDo(print());
    }
}
