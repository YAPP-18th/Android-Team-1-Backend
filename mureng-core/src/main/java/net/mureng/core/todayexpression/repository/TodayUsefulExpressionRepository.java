package net.mureng.core.todayexpression.repository;

import net.mureng.core.todayexpression.entity.TodayUsefulExpression;
import net.mureng.core.todayexpression.entity.UsefulExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayUsefulExpressionRepository extends JpaRepository<TodayUsefulExpression, Long> {
}
