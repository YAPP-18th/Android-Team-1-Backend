package net.mureng.mureng.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional // TODO validation 적용
    public Member signup(Member newMember) {
        return memberRepository.saveAndFlush(newMember);
    }
}
