package net.mureng.mureng.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.core.exception.BadRequestException;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(@Valid Member newMember) {
        if (isNicknameDuplicated(newMember.getNickname())) {
            throw new BadRequestException("중복된 닉네임입니다.");
        }

        return memberRepository.saveAndFlush(newMember);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean isEmailExist(String email) { return memberRepository.existsByEmail(email); }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email) { return memberRepository.findByEmail(email); }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) { return memberRepository.findById(memberId)
            .orElseThrow(()->new BadRequestException("존재하지 않는 사용자에 대한 요청입니다.")); }
}
