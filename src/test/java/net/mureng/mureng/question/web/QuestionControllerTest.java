package net.mureng.mureng.question.web;

import net.mureng.mureng.annotation.WithMockMurengUser;
import net.mureng.mureng.common.EntityCreator;
import net.mureng.mureng.question.entity.Question;
import net.mureng.mureng.question.service.QuestionService;
import net.mureng.mureng.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionControllerTest extends AbstractControllerTest {
    @MockBean
    private QuestionService questionService;

    @Test
    @WithMockMurengUser
    public void 질문_목록_페이징_조회_테스트() throws Exception {
        int page = 0;
        int size = 2;

        List<Question> questionList = new ArrayList<>();
        questionList.add(EntityCreator.createQuestionEntity());
        questionList.add(EntityCreator.createQuestionEntity());

        Page<Question> questionPage = new PageImpl<>(questionList);

        given(questionService.getQuestionList(eq(page), eq(size))).willReturn(questionPage);

        mockMvc.perform(
                get("/api/questions?page=0&size=2")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].questionId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].questionId").value(1))
                .andDo(print());
    }
}
