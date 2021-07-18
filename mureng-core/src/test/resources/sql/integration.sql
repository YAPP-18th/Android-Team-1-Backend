INSERT INTO member (member_id, email, identifier, image, is_active, attendance_count, last_attendance_date, is_daily_push_active, mod_date, nickname, reg_date, is_like_push_active) VALUES (1, 'test@gmail.com', 'identity', '/reply/1621586761110.png', true, 0, '2021-05-13', true, '2020-10-14 17:11:09.400891', '테스트유저', '2020-10-14 17:11:09.400876', true);
INSERT INTO member (member_id, email, identifier, image, is_active, attendance_count, last_attendance_date, is_daily_push_active, mod_date, nickname, reg_date, is_like_push_active) VALUES (2, 'test2@email.com', 'identity2', null, true, 0, '2020-12-05', true, '2021-05-13 02:59:27.582760', '테스트유저2', '2021-05-13 02:59:27.582760', true);
INSERT INTO member (member_id, email, identifier, image, is_active, attendance_count, last_attendance_date, is_daily_push_active, mod_date, nickname, reg_date, is_like_push_active) VALUES (3, 'test3@email.com', 'identity3', '/reply/1621586761110.png', true, 0, '2021-05-13', false, '2021-07-08 11:30:00.184937', '테스트유저3', '2021-05-13 01:31:33.400876', true);

INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (1, '테스트', 'This is test question.', '이것은 테스트 질문입니다.', '2021-05-13 02:54:36', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (2, '테스트2', 'This is test question2.', '이것은 테스트 질문2입니다.', '2021-05-18 13:47:07', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (3, '테스트3', 'This is user-defined question.', '이것은 사용자가 등록한 질문입니다.', '2021-05-18 13:47:08', 1);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (4, '테스트', 'This is test question3.', '이것은 테스트 질문3입니다.', '2021-05-18 13:47:09', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (5, '테스트', 'This is test question4.', '이것은 테스트 질문4입니다.', '2021-05-18 13:47:10', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (6, '테스트', 'This is test question5.', '이것은 테스트 질문5입니다.', '2021-05-18 13:47:11', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (7, '테스트', 'This is test question6.', '이것은 테스트 질문6입니다.', '2021-05-18 13:47:12', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (8, '테스트', 'This is test question7.', '이것은 테스트 질문7입니다.', '2021-05-18 13:47:13', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (9, '테스트', 'This is test question8.', '이것은 테스트 질문8입니다.', '2021-05-18 13:47:14', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (10, '테스트', 'This is test question9.', '이것은 테스트 질문9입니다.', '2021-05-18 13:47:15', null);
INSERT INTO question (question_id, category, content, ko_content, reg_date, member_id) VALUES (11, '테스트', 'This is test question10.', '이것은 테스트 질문10입니다.', '2021-05-18 13:47:16', null);

INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (1, 'this is test reply 1', false, '/reply/1621586761110.png', '2021-05-18 12:48:01', '2021-05-18 12:49:01', true, 1, 1);
INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (2, 'this is test reply 2', false, '/reply/1621586761110.png', '2021-05-18 12:48:02', '2021-05-18 12:49:02', true, 1, 2);
INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (3, 'this is test reply 3', false, '/reply/1621586761110.png', '2021-05-18 12:48:03', '2021-05-18 12:49:03', true, 2, 1);
INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (4, 'this is test reply 4', false, '/reply/1621586761110.png', '2021-05-18 12:48:04', '2021-05-18 12:49:04', true, 2, 2);
INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (5, 'this is test reply 5', false, '/reply/1621586761110.png', '2021-05-18 12:48:05', '2021-05-18 12:49:05', true, 3, 1);
INSERT INTO reply (reply_id, content, deleted, image, mod_date, reg_date, visible, member_id, question_id) VALUES (6, 'this is test reply 6', false, '/reply/1621586761110.png', '2021-05-18 12:48:06', '2021-05-18 12:49:06', true, 3, 3);

INSERT INTO reply_likes (member_id, reply_id, reg_date) VALUES (1, 1, '2021-07-01 18:58:26');
INSERT INTO reply_likes (member_id, reply_id, reg_date) VALUES (2, 1, '2021-07-03 11:42:26');
INSERT INTO reply_likes (member_id, reply_id, reg_date) VALUES (1, 3, '2021-07-03 14:58:12');

INSERT INTO word_hint (hint_id, meaning, reg_date, word, question_id) VALUES (1, '의미 1', '2021-05-14 01:31:18', 'meaning 1', 1);
INSERT INTO word_hint (hint_id, meaning, reg_date, word, question_id) VALUES (2, '의미 2', '2021-05-14 01:31:18', 'meaning 2', 2);
INSERT INTO word_hint (hint_id, meaning, reg_date, word, question_id) VALUES (3, '의미 3', '2021-05-14 01:31:18', 'meaning 3', 3);
INSERT INTO word_hint (hint_id, meaning, reg_date, word, question_id) VALUES (4, '의미 4', '2021-05-14 01:31:18', 'meaning 4', 4);

INSERT INTO today_question (member_id, mod_date, reg_date, question_id) VALUES (1, '2021-05-13 02:55:27', '2021-05-13 02:55:29', 1);
INSERT INTO today_question (member_id, mod_date, reg_date, question_id) VALUES (2, '2021-05-13 02:59:27', '2021-05-13 02:59:27', 2);
INSERT INTO today_question (member_id, mod_date, reg_date, question_id) VALUES (3, '2021-05-13 02:59:28', '2021-05-13 02:59:28', 3);

INSERT INTO useful_expression (exp_id, expression, expression_example, expression_example_meaning, meaning, mod_date, reg_date) VALUES (1, 'I''m sure that ~', 'I''m sure that I will achieve my goal.', '난 내 목표를 달성할 거라고 확신해.', '~라는 것을 확신해', '2021-05-29 14:21:12', '2021-05-29 14:21:09');
INSERT INTO useful_expression (exp_id, expression, expression_example, expression_example_meaning, meaning, mod_date, reg_date) VALUES (2, 'I''m happy to ~', 'I''m happy to see you again.', '널 다시 보게 돼서 기뻐.', '~해서 기뻐', '2021-05-29 14:21:49', '2021-05-29 14:21:48');
INSERT INTO useful_expression (exp_id, expression, expression_example, expression_example_meaning, meaning, mod_date, reg_date) VALUES (3, 'I''m looking forward to ~ing', 'I''m looking forward to seeing you tomorrow.', '내일 널 보는 것이 기대돼.', '~이 너무 기대돼', '2021-06-28 00:55:57', '2021-06-28 00:56:10');
INSERT INTO useful_expression (exp_id, expression, expression_example, expression_example_meaning, meaning, mod_date, reg_date) VALUES (4, 'I think that ~', 'I think that happiness is important in life.', '인생에서 행복이 중요하다고 생각해.', '~라고 생각해', '2021-06-28 00:55:57', '2021-06-28 00:56:12');

INSERT INTO today_useful_expression (id, exp_id, mod_date, reg_date) VALUES (1, 1, '2021-05-29 14:21:12', '2021-05-29 14:21:09');
INSERT INTO today_useful_expression (id, exp_id, mod_date, reg_date) VALUES (2, 2, '2021-05-29 14:21:12', '2021-05-29 14:21:09');

INSERT INTO member_scrap (exp_id, member_id, reg_date) VALUES (2, 1, '2021-06-20 18:53:24.110939');
INSERT INTO member_scrap (exp_id, member_id, reg_date) VALUES (3, 1, '2021-07-08 11:30:31.503929');
INSERT INTO member_scrap (exp_id, member_id, reg_date) VALUES (1, 2, '2021-06-21 00:04:21.890788');

INSERT INTO badge (badge_id, content, name) VALUES (1, '머렝 3일입니다.', '머렝 3일');
INSERT INTO badge (badge_id, content, name) VALUES (2, '셀럽 머렝입니다.', '셀럽 머렝');
INSERT INTO badge (badge_id, content, name) VALUES (3, '학구정 머렝입니다.', '학구적 머렝');
INSERT INTO badge (badge_id, content, name) VALUES (4, '머렝 셋입니다.', '머렝 셋');

INSERT INTO badge_accomplished (badge_id, member_id, reg_date, is_checked) VALUES (1, 1, '2021-07-08 11:13:51.113911', false);
INSERT INTO badge_accomplished (badge_id, member_id, reg_date, is_checked) VALUES (2, 1, '2021-07-08 11:13:51.113911', false);
