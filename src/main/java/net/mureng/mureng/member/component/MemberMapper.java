package net.mureng.mureng.member.component;

import net.mureng.mureng.core.component.EntityMapper;
import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", imports = DateTimeFormatter.class)
public interface MemberMapper extends EntityMapper<Member, MemberDto> {
    @Override
    @Mapping(target = "attendanceCount", expression = "java(member.getMemberAttendance().getAttendanceCount())")
    @Mapping(target = "lastAttendanceDate", expression = "java(member.getMemberAttendance().getLastAttendanceDate()" +
            ".format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) )")
    @Mapping(target = "pushActive", expression = "java(member.getMemberSetting().isPushActive())")
    MemberDto.ReadOnly toDto(Member member);

    @Override
    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "murengCount", ignore = true)
    @Mapping(target = "memberSetting", ignore = true)
    @Mapping(target = "memberAttendance", ignore = true)
    @Mapping(target = "regDate", ignore = true)
    @Mapping(target = "modDate", ignore = true)
    Member toEntity(MemberDto memberDto);
}
