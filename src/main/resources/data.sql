# INSERT INTO guardian (id, login_id, kakao_id, phone, name, email, push_notification, status, is_deleted)
# VALUES (1, 'guardian1', 1234567890, '01012345678', '보호자1', 'guardian@example.com', true, 'ACTIVE', false);
#
# INSERT INTO subject (id, login_id, guardian_id, kakao_id, connect_code, guardian_name, phone, name, email, push_notification, status, is_deleted)
# VALUES (1001, 'subject1', 1, 1234567891, 'ABC1234', '보호자1', '01023456789', '대상자1', 'subject@example.com', true, 'ACTIVE', false),
#        (1002, 'subject2', 1, 1234567892, 'XYZ5678', '보호자1', '01034567890', '대상자2', 'subject2@example.com', true, 'ACTIVE', false);
#

INSERT INTO mission (
    guardian_id, subject_id, title, description, requires_photo,
    mission_start_time, mission_end_time, is_repeated, repeat_day,
    mission_date, mission_month, importance, status, mission_completed_time
) VALUES
-- 오늘 날짜 기준 미션 (예: 2025-07-05)
(1, 1001, '아침 산책하기', '공원 한 바퀴 돌기', true,
 '08:00:00', '08:30:00', false, NULL,
 '2025-07-05', '2025-07', 'HIGH', 'NOT_STARTED', NULL),

-- 오늘 날짜 + 완료됨
(1, 1001, '약 먹기', '오전 약 복용', false,
 '09:00:00', '09:10:00', false, NULL,
 '2025-07-05', '2025-07', 'LOW', 'COMPLETED', '09:05:00'),

-- 어제 날짜
(1, 1001, '어제 운동하기', '실내 자전거 10분', true,
 '10:00:00', '10:30:00', false, NULL,
 '2025-07-04', '2025-07', 'MEDIUM', 'COMPLETED', '10:25:00'),

-- 다른 대상자
(1, 1002, '물 마시기', '물 500ml 마시기', false,
 '11:00:00', '11:05:00', false, NULL,
 '2025-07-05', '2025-07', 'MEDIUM', 'NOT_STARTED', NULL);
