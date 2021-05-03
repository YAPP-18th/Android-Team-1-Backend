package net.mureng.mureng.member.component;

import net.mureng.mureng.member.dto.MemberDto;
import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.member.entity.MemberAttendance;
import net.mureng.mureng.member.entity.MemberSetting;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    protected MemberMapper() {
        final Converter<MemberDto, MemberAttendance> memberAttendanceConverter = context -> {
            if (context == null)
                return null;

            return MemberAttendance.builder()
                    .memberId(context.getSource().getMemberId())
                    .attendanceCount(context.getSource().getAttendanceCount())
                    .lastAttendanceDate(context.getSource().getLastAttendanceDate())
                    .build();
        };

        final Converter<MemberDto, MemberSetting> memberSettingConverter = context -> {
            if (context == null)
                return null;

            return MemberSetting.builder()
                    .memberId(context.getSource().getMemberId())
                    .dailyEndTime(context.getSource().getDailyEndTime())
                    .isPushActive(context.getSource().isPushActive())
                    .build();
        };

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.createTypeMap(Member.class, MemberDto.class)
                .addMapping(src -> src.getMemberAttendance().getAttendanceCount(), MemberDto::setAttendanceCount)
                .addMapping(src -> src.getMemberAttendance().getLastAttendanceDate(), MemberDto::setLastAttendanceDate)
                .addMapping(src -> src.getMemberSetting().getDailyEndTime(), MemberDto::setDailyEndTime)
                .addMapping(src -> src.getMemberSetting().isPushActive(), MemberDto::setPushActive);
        modelMapper.createTypeMap(MemberDto.class, Member.class)
                .addMappings(mapper -> mapper.using(memberAttendanceConverter)
                        .map(source -> source, Member::setMemberAttendance))
                .addMappings(mapper -> mapper.using(memberSettingConverter)
                        .map(source -> source, Member::setMemberSetting))
                .addMappings(mapper -> mapper.skip(Member::setRegDate))
                .addMappings(mapper -> mapper.skip(Member::setModDate));
        modelMapper.validate();
    }

    public Member map(MemberDto memberDto) {
        return modelMapper.map(memberDto, Member.class);
    }

    public MemberDto map(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }
}
