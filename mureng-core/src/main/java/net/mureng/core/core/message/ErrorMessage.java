package net.mureng.core.core.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    // Authorization
    public static String UNAUTHORIZED = "접근 권한이 없습니다.";

    // Member
    public static String NOT_EXIST_MEMBER = "존재하지 않는 사용자입니다.";
    public static String DUPLICATED_NICKNAME = "이미 사용 중인 닉네임입니다.";
    public static String ALREADY_ANSWERED_MEMBER = "이미 오늘 답변한 사용자입니다.";

    // Question
    public static String NOT_EXIST_QUESTION = "존재하지 않는 질문입니다.";

    // Badge
    public static String NOT_EXIST_BADGE = "존재하지 않는 뱃지입니다.";
    public static String NOT_ACCOMPLISHED_BADGE = "획득하지 않은 뱃지 번호입니다.";
    public static String MEMBER_CHECK_BADGE_ACCOMPLISHED = "사용자가 뱃지 획득을 확인하였습니다.";

    // Reply
    public static String NOT_EXIST_REPLY = "존재하지 않는 답변입니다.";
    public static String ALREADY_ANSWERED_REPLY = "이미 답변한 질문입니다.";

    // ReplyLikes
    public static String ALREADY_PUSHED_REPLY_LIKE = "이미 좋아요를 눌렀습니다.";
    public static String ALREADY_CANCELED_REPLY_LIKE = "이미 좋아요를 취소했습니다.";

    // File Directory
    public static String NOT_DIRECTORY = "Not a directory";

    // Today Question
    public static String QUESTION_REFRESH_FAIL = "질문 새로 고침에 실패하였습니다.";

    // Today Expression
    public static String EXPRESSION_REFRESH_FAIL = "오늘의 표현 새로 고침에 실패하였습니다.";
    public static String NOT_EXIST_EXPRESSION = "존재하지 않는 오늘의 표현에 대한 요청입니다.";

    // Cookie
    public static String ALREADY_COOKIE_ACQUIRED = "이미 오늘 쿠키를 획득하였습니다.";

    // Scrap
    public static String ALREADY_SCRAPPED_EXPRESSION = "이미 스크랩한 표현입니다.";
    public static String ALREADY_CANCELED_EXPRESSION = "이미 스크랩을 취소한 표현입니다.";

}
