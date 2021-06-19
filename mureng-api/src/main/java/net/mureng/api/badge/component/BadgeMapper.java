package net.mureng.api.badge.component;

import net.mureng.api.badge.dto.BadgeDto;
import net.mureng.core.badge.entity.Badge;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BadgeMapper {
    BadgeDto toDto(Badge badge);
}
