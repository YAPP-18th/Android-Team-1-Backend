package net.mureng.mureng.core.component;

import org.mapstruct.factory.Mappers;

public interface EntityMapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
