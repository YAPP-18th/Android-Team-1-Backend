package net.mureng.api.core.component;

public interface EntityMapper<ENTITY, DTO> {
    DTO toDto(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
