CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO USERS (user_name, nick_name, gender, is_active, type, description, created_at, updated_at, deleted_at)
VALUES ('user_acro_1', 'Nick1', 'male', TRUE, 'back', 'user_acro_ 1 description', NOW(), NOW(), null),
       ('user_acro_2', 'Nick2', 'female', TRUE, 'front', 'user_acro_ 2 description', NOW(), NOW(), null),
       ('user_acro_3', 'Nick3', 'male', FALSE, 'back', 'user_acro_ 3 description', NOW(), NOW(), null),
       ('user_acro_4', 'Nick4', 'female', TRUE, 'admin', 'user_acro_ 4 description', NOW(), NOW(), null),
       ('user_acro_5', 'Nick5', 'male', TRUE, 'infra', 'user_acro_ 5 description', NOW(), NOW(), null),
       ('user_acro_6', 'Nick6', 'female', FALSE, 'front', 'user_acro_ 6 description', NOW(), NOW(), null),
       ('user_acro_7', 'Nick7', 'male', TRUE, 'back', 'user_acro_ 7 description', NOW(), NOW(), null),
       ('user_acro_8', 'Nick8', 'female', FALSE, 'admin', 'user_acro_ 8 description', NOW(), NOW(), null),
       ('user_acro_9', 'Nick9', 'male', TRUE, 'infra', 'user_acro_ 9 description', NOW(), NOW(), null),
       ('user_acro_10', 'Nick10', 'female', FALSE, 'back', 'user_acro_ 10 description', NOW(), NOW(), null);

INSERT INTO ADMINS (user_name, nick_name, role, password, created_at, updated_at, deleted_at)
VALUES ('admin1', 'AdminNick1', 'ROLE_ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin2', 'AdminNick2', 'ROLE_ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin3', 'AdminNick3', 'ROLE_ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null),
       ('admin4', 'AdminNick4', 'ROLE_ADMIN', crypt('password1!', gen_salt('bf')), NOW(), NOW(), null);

