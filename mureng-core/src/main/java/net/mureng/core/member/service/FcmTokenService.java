package net.mureng.core.member.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.member.entity.FcmToken;
import net.mureng.core.member.entity.Member;
import net.mureng.core.member.repository.FcmTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {
    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public void insertToken(String token) {
        if (fcmTokenRepository.existsByToken(token)) {
            return;
        }
        fcmTokenRepository.saveAndFlush(FcmToken.builder().token(token).build());
    }

    @Transactional
    public void updateTokenOfMember(String token, Member member) {
        // 토큰이 이미 존재한다면, 요청자 토큰으로 업데이트 한다.
        if (fcmTokenRepository.existsByToken(token)) {
            FcmToken fcmToken = fcmTokenRepository.findByToken(token).orElseThrow();
            fcmToken.setMember(member);
            fcmTokenRepository.saveAndFlush(fcmToken);
            return;
        }

        // 토큰이 존재하지 않으면 새로 생성
        Optional<FcmToken> fcmTokenOptional = fcmTokenRepository.findByMemberMemberId(member.getMemberId());
        if (fcmTokenOptional.isEmpty()) {
            fcmTokenRepository.saveAndFlush(FcmToken.builder()
                    .member(member)
                    .token(token)
                    .build());
            return;
        }

        FcmToken fcmToken = fcmTokenOptional.get();
        fcmToken.setToken(token);
        fcmTokenRepository.saveAndFlush(fcmToken);
    }
}
