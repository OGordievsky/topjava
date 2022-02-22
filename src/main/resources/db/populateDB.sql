DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meals_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-01-30 10:00:00', 'Завтрак', 500 ),
       (100000, '2020-01-30 13:00:00', 'Обед', 1000),
       (100000, '2020-01-30 20:00:00', 'Ужин', 500 ),
       (100000, '2020-01-31 0:00:00', 'Еда на граничное значение', 100 ),
       (100000, '2020-01-31 10:00:00', 'Завтрак', 1000),
       (100000, '2020-01-31 13:00:00', 'Обед', 500 ),
       (100000, '2020-01-31 20:00:00', 'Ужин', 410 ),
       (100000, '2020-02-1 10:00:00', 'Завтрак', 600 ),
       (100001, '2020-01-30 10:00:00', 'Завтрак Admin', 500 ),
       (100001, '2020-01-30 13:00:00', 'Обед Admin', 1000),
       (100001, '2020-01-30 20:00:00', 'Ужин Admin', 500 ),
       (100001, '2020-01-31 0:00:00', 'Еда на граничное значение Admin', 100 ),
       (100001, '2020-01-31 10:00:00', 'Завтрак Admin', 1000),
       (100001, '2020-01-31 13:00:00', 'Обед Admin', 500 ),
       (100001, '2020-01-31 20:00:00', 'Ужин Admin', 410 ),
       (100001, '2020-02-1 10:00:00', 'Завтрак Admin', 600 );