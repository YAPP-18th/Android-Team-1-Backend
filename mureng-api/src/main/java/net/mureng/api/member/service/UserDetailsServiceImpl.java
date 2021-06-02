package net.mureng.api.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.dto.UserDetailsImpl;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdentifier(identifier).orElseThrow();

        return new UserDetailsImpl(member);
    }
}

