DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants(id, name)
values (100001, 'Bartolomeo'),
       (100002, 'Khutor'),
       (100003, 'Celentano');


INSERT INTO meals(restaurant_id, description, price)
VALUES (100001, 'Milk', 35),
       (100001, 'Cake', 50),
       (100001, 'Ice cream', 20),

       (100002, 'Soup', 25),
       (100002, 'Omelette', 35),
       (100002, 'Salad', 20),
       (100002, 'Juice', 15),
       (100002, 'Bread', 10),

       (100003, 'Pizza', 120),
       (100003, 'Beer', 50);