package net.mureng.mureng.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEMBER_SCRAP")
public class MemberScrap {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "exp_id")
    private Long expId;

    @Column(name = "reg_date")
    private LocalDate regDate;
}
