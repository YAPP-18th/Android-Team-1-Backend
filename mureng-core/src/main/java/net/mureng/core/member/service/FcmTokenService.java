package net.mureng.core.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.FcmToken;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.FcmTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class FcmTokenService {
    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public void insertToken(String token) {
        fcmTokenRepository.saveAndFlush(FcmToken.builder().token(token).build());
    }

    @Transactional
    public void updateTokenOfMember(String token, Member member) {
        FcmToken fcmToken = fcmTokenRepository.findByMember(member)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        fcmToken.setToken(token);
        fcmTokenRepository.saveAndFlush(fcmToken);
    }
}
