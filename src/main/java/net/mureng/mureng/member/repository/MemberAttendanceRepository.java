package net.mureng.mureng.member.repository;

import net.mureng.mureng.member.entity.MemberAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAttendanceRepository extends JpaRepository<MemberAttendance, Long> {
}
