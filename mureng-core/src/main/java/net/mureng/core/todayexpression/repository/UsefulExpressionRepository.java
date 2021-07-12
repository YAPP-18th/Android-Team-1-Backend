package net.mureng.core.todayexpression.repository;

import net.mureng.core.todayexpression.entity.UsefulExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsefulExpressionRepository extends JpaRepository<UsefulExpression, Long> {
}
