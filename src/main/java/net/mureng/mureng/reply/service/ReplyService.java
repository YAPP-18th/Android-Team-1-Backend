package net.mureng.mureng.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.mureng.reply.entity.Reply;
import net.mureng.mureng.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Reply postReply(@Valid Reply newReply) {
        return replyRepository.saveAndFlush(newReply);
    }
}
