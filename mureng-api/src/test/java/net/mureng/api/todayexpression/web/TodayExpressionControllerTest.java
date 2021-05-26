package net.mureng.api.todayexpression.web;

import net.mureng.api.annotation.WithMockMurengUser;
import net.mureng.api.web.AbstractControllerTest;
import net.mureng.core.common.EntityCreator;
import net.mureng.core.todayexpression.entity.TodayExpression;
import net.mureng.core.todayexpression.service.TodayExpressionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodayExpressionControllerTest extends AbstractControllerTest {

    @MockBean
    private TodayExpressionService todayExpressionService;

    @Test
    @WithMockMurengUser
    public void 오늘의_표현_가져오기_테스트() throws Exception {
        TodayExpression todayExpression1 = EntityCreator.createTodayExpressionEntity();
        TodayExpression todayExpression2 = EntityCreator.createTodayExpressionEntity();
        List<TodayExpression> todayExpressionList = Arrays.asList(todayExpression1, todayExpression2);

        int page = 0;
        int size = 2;

        given(todayExpressionService.getTodayExpressions()).willReturn(new PageImpl<>(todayExpressionList, PageRequest.of(page, size), 2));

        mockMvc.perform(
                get("/api/today-expression")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].meaning").value("테스트"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExample").value("test driven development"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].expressionExampleMeaning").value("테스트 주도 개발"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].expression").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].meaning").value("테스트"))
                .andDo(print());
    }
}
