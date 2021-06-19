package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.repository.BadgeRepository;
import net.mureng.core.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;

    @Transactional(readOnly = true)
    public Badge findById(Long badgeId){
        return badgeRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 뱃지를 찾을 수 없습니다."));
    }
}
