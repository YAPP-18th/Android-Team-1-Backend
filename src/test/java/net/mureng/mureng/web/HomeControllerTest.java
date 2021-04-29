package net.mureng.mureng.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class HomeControllerTest extends AbstractControllerTest{

    @Autowired
    private HomeController homeController;

    @Override
    protected Object controller() {
        return homeController;
    }

    @Test
    public void 테스트_GET_비인가사용자() throws Exception {
        mockMvc.perform(
                get("/api/test")
        );
    }

    @Test
    public void 테스트_GET_인가사용자() throws Exception {

    }

}