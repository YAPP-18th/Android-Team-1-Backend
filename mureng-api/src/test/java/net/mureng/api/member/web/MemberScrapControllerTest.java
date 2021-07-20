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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberScrapControllerTest extends AbstractControllerTest {
    private static final long EXP_ID = 1L;
    private static final long MEMBER_ID = 1L;

    @Test
    @WithMockMurengUser
    public void 오늘의표현_스크랩_추가_테스트() throws Exception{
        mockMvc.perform(
                post("/api/member/scrap/{expId}", EXP_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expression").value("I'm sure that ~"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.meaning").value("~라는 것을 확신해"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressionExample").value("I'm sure that I will achieve my goal."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressionExampleMeaning").value("난 내 목표를 달성할 거라고 확신해."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrappedByRequester").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 오늘의표현_스크랩_삭제_테스트() throws Exception{
        mockMvc.perform(
                delete("/api/member/scrap/{expId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.deleted").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 사용자_스크랩_목록_가져오기_테스트() throws Exception {
        mockMvc.perform(
                get("/api/member/{memberId}/scrap", MEMBER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.memberId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrapList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requesterProfile").value(true))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 내_스크랩_목록_가져오기_테스트() throws Exception {
        mockMvc.perform(
                get("/api/member/me/scrap")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.memberId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.scrapList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requesterProfile").value(true))
                .andDo(print());
    }
}
