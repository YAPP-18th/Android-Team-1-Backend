package net.mureng.mureng.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    private String identifier;

    private String email;

    private Boolean is_active;

    private String nickname;

    private String image;

    private LocalDateTime reg_date;

    private LocalDateTime mod_date;
}
