package net.mureng.core.badge.service;

public interface BadgeAccomplishedService {
    boolean createMureng3Days(Long memberId);
    boolean createCelebrityMureng(Long replyId);
    boolean createAcademicMureng(Long memberId);
    boolean createMurengSet(Long memberId);
    boolean isAlreadyCheckedCelebrityMureng(Long memberId);
}
