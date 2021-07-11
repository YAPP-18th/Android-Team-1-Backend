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

    // Reply
    public static String NOT_EXIST_REPLY = "존재하지 않는 답변입니다.";
    public static String ALREADY_ANSWERED_REPLY = "이미 답변한 질문입니다.";

    // File Directory
    public static String NOT_DIRECTORY = "Not a directory";

    // Today Question
    public static String QUESTION_REFRESH_FAIL = "질문 새로 고침에 실패하였습니다.";

    // Today Expression
    public static String EXPRESSION_REFRESH_FAIL = "오늘의 표현 새로 고침에 실패하였습니다.";

    // Cookie
    public static String ALREADY_COOKIE_ACQUIRED = "이미 오늘 쿠키를 획득하였습니다.";


}
