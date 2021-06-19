package net.mureng.api.reply.service;

import lombok.RequiredArgsConstructor;
import net.mureng.api.core.dto.ApiPageRequest;
import net.mureng.core.reply.entity.Reply;
import net.mureng.core.reply.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyPaginationService {
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public Page<Reply> findRepliesByQuestionId(Long questionId, ApiPageRequest pageRequest) {
        if (pageRequest.getSort() == ApiPageRequest.PageSort.POPULAR)
            return replyRepository.findAllByQuestionQuestionIdOrderByReplyLikesSize(questionId, pageRequest.convert());

        return replyRepository.findAllByQuestionQuestionId(questionId, pageRequest.convertWithNewestSort());
    }

    @Transactional(readOnly = true)
    public Page<Reply> findReplies(ApiPageRequest pageRequest) {
        if (pageRequest.getSort() == ApiPageRequest.PageSort.POPULAR)
            return replyRepository.findAllByOrderByReplyLikesSize(pageRequest.convert());

        return replyRepository.findAll(pageRequest.convertWithNewestSort());
    }
}
