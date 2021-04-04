package net.mureng.mureng.domain.member;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class MemberSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long member_id;

    private LocalTime daily_end_time;

    private Boolean is_push_active;
}
