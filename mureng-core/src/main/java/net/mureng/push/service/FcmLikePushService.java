package net.mureng.push.service;

import net.mureng.core.member.entity.Member;
import net.mureng.core.reply.entity.Reply;

public interface FcmLikePushService {
    void pushToAuthor(Reply reply, Member likedMember);
}
