package net.mureng.core.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.core.exception.BadRequestException;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

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
    public boolean isMemberExist(String identifier) { return memberRepository.existsByIdentifier(identifier); }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) { return memberRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자에 대한 요청입니다.")); }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) { return memberRepository.findById(memberId)
            .orElseThrow(()->new ResourceNotFoundException("존재하지 않는 사용자에 대한 요청입니다.")); }
}
