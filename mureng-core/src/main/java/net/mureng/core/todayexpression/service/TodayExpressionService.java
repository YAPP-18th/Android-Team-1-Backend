package net.mureng.core.todayexpression.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.todayexpression.entity.TodayExpression;
import net.mureng.core.todayexpression.repository.TodayExpressionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class TodayExpressionService {
    private final TodayExpressionRepository todayExpressionRepository;
    /*
     * @To do:
     * 다른 방식으로 차후에 변경 가능성 존재
     */
    private final LocalDate MurengLaunchingDate = LocalDate.of(2021, 05, 24);

    @Transactional(readOnly = true)
    public Page<TodayExpression> getTodayExpressions(){

        LocalDate today = LocalDate.now();
        long totalElements = todayExpressionRepository.count();
        long page = ChronoUnit.DAYS.between(MurengLaunchingDate, today) % (totalElements * 2);

        return todayExpressionRepository.findAll(PageRequest.of((int) page,2));
    }
}
