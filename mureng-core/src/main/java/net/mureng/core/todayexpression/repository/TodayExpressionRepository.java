package net.mureng.core.todayexpression.repository;

import net.mureng.core.todayexpression.entity.TodayExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayExpressionRepository extends JpaRepository<TodayExpression, Long> {
}
