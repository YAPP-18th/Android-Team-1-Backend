package net.mureng.core.badge.service;

import lombok.RequiredArgsConstructor;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.badge.repository.BadgeRepository;
import net.mureng.core.core.exception.ResourceNotFoundException;
import net.mureng.core.core.exception.business.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static net.mureng.core.core.message.ErrorMessage.NOT_EXIST_BADGE;

@RequiredArgsConstructor
@Service
public class BadgeService {
    private final BadgeRepository badgeRepository;

    @Transactional(readOnly = true)
    public Badge findById(Long badgeId){
        return badgeRepository.findById(badgeId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXIST_BADGE));
    }
}
