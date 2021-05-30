package net.mureng.api.badge;

import net.mureng.api.badge.component.BadgeMapper;
import net.mureng.api.badge.dto.BadgeDto;
import net.mureng.api.common.DtoCreator;
import net.mureng.core.badge.entity.Badge;
import net.mureng.core.common.EntityCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BadgeMapperTest {

    @Autowired
    private BadgeMapper badgeMapper;

    private static final Badge badge = EntityCreator.createBadgeEntity();
    private static final BadgeDto badgeDto = DtoCreator.createBadgeDto();

    @Test
    public void 엔티티에서_DTO변환_테스트() {
        BadgeDto mappedDto = badgeMapper.toDto(badge);

        assertEquals(mappedDto.getName(), badgeDto.getName());
        assertEquals(mappedDto.getContent(), badgeDto.getContent());
    }
}
