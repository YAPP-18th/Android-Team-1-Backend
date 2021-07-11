package net.mureng.batch.todayexpression.refresh.service;

import net.mureng.core.core.component.NumberRandomizer;
import net.mureng.core.todayexpression.entity.TodayUsefulExpression;
import net.mureng.core.todayexpression.repository.TodayUsefulExpressionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Sql("classpath:/sql/integration.sql")
@SpringBootTest
class TodayUsefulExpressionRefreshServiceTest {
    @Autowired
    private TodayUsefulExpressionRefreshServiceImpl todayUsefulExpressionRefreshService;

    @Autowired
    private TodayUsefulExpressionRepository todayUsefulExpressionRepository;
    
    @MockBean
    private NumberRandomizer numberRandomizer;

    @Test
    @Transactional
    void 오늘의_표현_갱신_테스트() {
        given(numberRandomizer.getRandomInt(anyInt())).willReturn(4, 3);

        todayUsefulExpressionRefreshService.reselectTodayUsefulExpression();

        List<TodayUsefulExpression> todayUsefulExpressions = todayUsefulExpressionRepository.findAll();
        assertEquals(2, todayUsefulExpressions.size());
        assertEquals(4, todayUsefulExpressions.get(0).getUsefulExpression().getExpId());
        assertEquals(3, todayUsefulExpressions.get(1).getUsefulExpression().getExpId());
    }
}