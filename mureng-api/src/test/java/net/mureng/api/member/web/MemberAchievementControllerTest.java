package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.member.service.MemberBadgeService;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberAchievementControllerTest extends AbstractControllerTest {

    @MockBean
    MemberBadgeService memberBadgeService;

    @MockBean
    MemberService memberService;

    private static final long MEMBER_ID = 1L;
    private static final List<BadgeAccomplished> badgeAccomplisheds = Arrays.asList(EntityCreator.createBadgeAccomplishedEntity(), EntityCreator.createBadgeAccomplishedEntity());

    private List<Badge> badges = Arrays.asList(EntityCreator.createBadgeEntity(), EntityCreator.createBadgeEntity());
    private Member member = EntityCreator.createMemberEntity();

    @Test
    @WithMockMurengUser
    public void 사용자_성과_가져오기_테스트() throws Exception{
        given(memberBadgeService.getMemberBadges(eq(MEMBER_ID))).willReturn(badgeAccomplisheds);
        given(memberService.findById(eq(MEMBER_ID))).willReturn(member);

        mockMvc.perform(
                get("/api/member/{memberId}/achievement", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.memberId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.member.email").value("test@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.badges[0].name").value("Badge Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.badges[0].content").value("Badge Test Content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.requesterProfile").value("true"))
                .andDo(print());
    }
}
