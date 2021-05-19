package net.mureng.mureng.member.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import net.mureng.mureng.member.service.MemberService;
import net.mureng.mureng.member.service.MemberSignupService;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.service.ReplyService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends AbstractControllerTest {
    private final Member newMember = Member.builder()
                                            .memberId(1L)
                                            .email("example@email.com")
                                            .identifier("user-identifier")
                                            .image("image-path")
                                            .nickname("최대여섯글자")
                                            .murengCount(0)
                                            .memberAttendance(MemberAttendance.builder()
                                                    .lastAttendanceDate(LocalDate.parse("2020-10-14"))
                                                    .attendanceCount(0)
                                                    .build())
                                            .memberSetting(MemberSetting.builder()
                                                    .isPushActive(true)
                                                    .build())
                                            .build();

    private final String newMemberJsonString = "{" +
                                                "  \"email\": \"example@email.com\",\n" +
                                                "  \"identifier\": \"user-identifier\",\n" +
                                                "  \"image\": \"image-path\",\n" +
                                                "  \"nickname\": \"최대여섯글자\",\n" +
                                                "  \"pushActive\": true\n" +
                                                "}";

    @MockBean
    MemberService memberService;

    @MockBean
    MemberSignupService memberSignupService;

    @MockBean
    ReplyService replyService;

    @Test
    public void 사용자_회원가입_테스트() throws Exception {
        given(memberSignupService.signup(any())).willReturn(newMember);

        mockMvc.perform(
                post("/api/member/signup")
                .content(newMemberJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("example@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.identifier").value("user-identifier"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.image").value("image-path"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastAttendanceDate").value("2020-10-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.murengCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickname").value("최대여섯글자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pushActive").value(true));
    }

    @Test
    public void 사용자_닉네임중복체크_테스트() throws Exception {
        given(memberService.isNicknameDuplicated(any())).willReturn(false);

        mockMvc.perform(
                get("/api/member/nickname-exists/테스트이름")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.duplicated").value(false));
    }

    @Test
    public void 사용자_닉네임중복체크_테스트2() throws Exception {
        given(memberService.isNicknameDuplicated(any())).willReturn(true);

        mockMvc.perform(
                get("/api/member/nickname-exists/테스트이름")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.duplicated").value(true));
    }

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