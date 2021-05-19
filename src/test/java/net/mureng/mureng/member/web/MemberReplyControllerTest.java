package net.mureng.mureng.member.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberReplyControllerTest extends AbstractControllerTest {
    @MockBean
    ReplyService replyService;

    @Test
    @WithMockMurengUser
    public void 사용자_답변_목록_조회_테스트() throws Exception {
        Member member = EntityCreator.createMemberEntity();
        List<Reply> replies = Arrays.asList(EntityCreator.createReplyEntity(), EntityCreator.createReplyEntity());

        given(replyService.findRepliesByMemberId(eq(1L))).willReturn(replies);

        mockMvc.perform(
                get("/api/member/replies")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 사용자_오늘_답변했는지_테스트() throws Exception {
        given(replyService.isAlreadyReplied(eq(1L))).willReturn(true);

        mockMvc.perform(
                get("/api/member/check-replied-today")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replied").value(true))
                .andDo(print());
    }
}