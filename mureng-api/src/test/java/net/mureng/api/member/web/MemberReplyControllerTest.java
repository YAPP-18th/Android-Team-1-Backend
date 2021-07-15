package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.service.ReplyService;
import net.mureng.api.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberReplyControllerTest extends AbstractControllerTest {
    @Test
    @WithMockMurengUser
    public void 사용자_답변_목록_조회_테스트() throws Exception {
        mockMvc.perform(
                get("/api/member/{memberId}/replies", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].replyId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("this is test reply 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].replyId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("this is test reply 1"))
                .andDo(print());
    }

    @Test
    @WithMockMurengUser
    public void 사용자_오늘_답변했는지_테스트() throws Exception {
        mockMvc.perform(
                get("/api/member/check-replied-today")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.replied").value(false))
                .andDo(print());
    }
}