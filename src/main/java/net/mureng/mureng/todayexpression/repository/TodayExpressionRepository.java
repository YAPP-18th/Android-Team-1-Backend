package net.mureng.mureng.todayexpression.repository;

import net.mureng.mureng.todayexpression.entity.TodayExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayExpressionRepository extends JpaRepository<TodayExpression, Long> {
}
