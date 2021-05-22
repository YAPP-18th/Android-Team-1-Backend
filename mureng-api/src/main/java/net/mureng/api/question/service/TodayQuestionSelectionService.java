package net.mureng.api.question.service;

import net.mureng.core.member.entity.Member;
import net.mureng.core.question.entity.Question;

public interface TodayQuestionSelectionService {
    Question reselectTodayQuestion(Member member);
}
