package net.mureng.api.member.component;

import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = DateTimeFormatter.class)
public interface MemberMapper {
    @Mapping(target = "murengCount", expression = "java(member.getMurengCookies().isEmpty() ? 0 : member.getMurengCookies().size())")
    @Mapping(target = "attendanceCount", expression = "java(member.getMemberAttendance().getAttendanceCount())")
    @Mapping(target = "lastAttendanceDate", expression = "java(member.getMemberAttendance().getLastAttendanceDate()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) )")
    MemberDto.ReadOnly toDto(Member member);

    Member toEntity(MemberDto memberDto);
}
