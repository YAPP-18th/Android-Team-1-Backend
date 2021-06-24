package net.mureng.api.member.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.member.service.MemberBadgeService;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.entity.BadgeAccomplished;
import net.mureng.core.badge.service.BadgeAccomplishedService;
import net.mureng.core.badge.service.BadgeAccomplishedServiceImpl;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberBadgeController extends AbstractControllerTest {
    @MockBean
    MemberBadgeService memberBadgeService;

    @Test
    @WithMockMurengUser
    public void 사용자_뱃지_획득_확인_테스트() throws Exception {
        given(memberBadgeService.updateBadgeAccomplished(any(), any())).willReturn(true);

        mockMvc.perform(
                put("/api/member/me/check/badge/{badgeId}",  1)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.isChecked").value(true))
                .andDo(print());
    }
}
