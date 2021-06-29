package net.mureng.core.reply.service;

import net.mureng.core.common.EntityCreator;
import net.mureng.core.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ReplyPostProcessServiceImplTest {

    @Test
    void postProcess() {
        Member member = EntityCreator.createMemberEntity();
        member.increaseMurengCount();

        assertEquals(member.getMurengCount(), 1);
    }
}