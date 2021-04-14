package net.mureng.mureng.member.repository;

import net.mureng.mureng.member.entity.MemberSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSettingRepository extends JpaRepository<MemberSetting, Long> {
}
