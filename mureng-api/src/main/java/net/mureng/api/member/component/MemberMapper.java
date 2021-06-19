package net.mureng.api.member.component;

import net.mureng.api.core.component.EntityMapper;
import net.mureng.api.member.dto.MemberDto;
import net.mureng.core.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = DateTimeFormatter.class)
public interface MemberMapper extends EntityMapper<Member, MemberDto> {
    @Override
    @Mapping(target = "attendanceCount", expression = "java(member.getMemberAttendance().getAttendanceCount())")
    @Mapping(target = "lastAttendanceDate", expression = "java(member.getMemberAttendance().getLastAttendanceDate()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) )")
    MemberDto.ReadOnly toDto(Member member);

    @Override
    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "murengCount", ignore = true)
    @Mapping(target = "memberSetting", ignore = true)
    @Mapping(target = "memberAttendance", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "modDate", ignore = true)
    Member toEntity(MemberDto memberDto);
}
