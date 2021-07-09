package net.mureng.core.todayexpression.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.todayexpression.entity.TodayUsefulExpression;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import net.mureng.core.todayexpression.repository.TodayUsefulExpressionRepository;
import net.mureng.core.todayexpression.repository.UsefulExpressionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsefulExpressionService {
    private final TodayUsefulExpressionRepository todayUsefulExpressionRepository;

    /**
     * 매일 변경하는 로직
     */
    @Transactional(readOnly = true)
    public Page<UsefulExpression> getTodayExpressions(){
        List<UsefulExpression> usefulExpressions = todayUsefulExpressionRepository.findAll().stream()
                .map(TodayUsefulExpression::getUsefulExpression)
                .collect(Collectors.toList());
        return new PageImpl<>(usefulExpressions);
    }
}
