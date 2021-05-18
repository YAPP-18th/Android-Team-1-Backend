package net.mureng.mureng.question.service;

import net.mureng.mureng.member.entity.Member;
import net.mureng.mureng.question.entity.Question;

public interface TodayQuestionSelectionService {
    Question reselectTodayQuestion(Member member);
}
