package net.mureng.api.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ErrorControllerTest extends AbstractControllerTest {
    @Test
    public void API호출_성공_테스트() throws Exception {
        mockMvc.perform(
                get("/api/test")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("ok"));
    }

    @Test
    public void API호출_실패_테스트() throws Exception {
        mockMvc.perform(
                get("/api/test-failure")
        ).andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("net.mureng.mureng failure"));
    }
}