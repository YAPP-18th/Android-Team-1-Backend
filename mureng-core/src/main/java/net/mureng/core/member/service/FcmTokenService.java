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
        Optional<FcmToken> fcmTokenOptionalFindByMemberId = fcmTokenRepository.findByMemberMemberId(member.getMemberId());
        Optional<FcmToken> fcmTokenOptionalFindByToken = fcmTokenRepository.findByToken(token);
        if (fcmTokenOptionalFindByMemberId.isPresent() && fcmTokenOptionalFindByToken.isPresent()) {
            // 둘 다 찾아질 경우 - 둘이 같은 것일 경우 - 아무것도 할 필요 없음
            if (fcmTokenOptionalFindByMemberId.get().getIdx().equals(fcmTokenOptionalFindByToken.get().getIdx())) {
                return;
            }

            // 둘 다 찾아질 경우 - 둘이 다른 것일 경우 - 회원에 찾은 값을 token 값으로 초기화 (기존 token객체 삭제)
            fcmTokenRepository.delete(fcmTokenOptionalFindByToken.get());
            fcmTokenRepository.flush();

            FcmToken fcmToken = fcmTokenOptionalFindByMemberId.get();
            fcmToken.setToken(token);
            fcmTokenRepository.saveAndFlush(fcmToken);

        } else if (fcmTokenOptionalFindByMemberId.isPresent()) {
            FcmToken fcmToken = fcmTokenOptionalFindByMemberId.get();
            fcmToken.setToken(token);
            fcmTokenRepository.saveAndFlush(fcmToken);

        } else if (fcmTokenOptionalFindByToken.isPresent()) {
            FcmToken fcmToken = fcmTokenOptionalFindByToken.get();
            fcmToken.setMember(member);
            fcmTokenRepository.saveAndFlush(fcmToken);

        } else {
            fcmTokenRepository.saveAndFlush(FcmToken.builder()
                    .member(member)
                    .token(token)
                    .build());
        }
    }
}
