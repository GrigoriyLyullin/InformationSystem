INSERT INTO user_role VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_role VALUES (2, 'ROLE_MANAGER');
INSERT INTO user_role VALUES (3, 'ROLE_USER');

-- password match with username
INSERT INTO user VALUES(1, 'admin', '$2a$10$cfTmbtnGdwvbVTZdWDWtCeqOZaow.ydOCbhF3L/73e7jGduwGXu3C', 1);
INSERT INTO user VALUES(2, 'manager', '$2a$10$dmcUyQgT1UXYBWqGxO1/Q.7r.F7r5wz/ZUFUUuDkuiRLtY/P3OjcK', 2);
INSERT INTO user VALUES(3, 'user', '$2a$10$wMXSxLJZ9gMJHV4/zLVB7efNSSZJ1xB36zZGoWJdKIZteWWZdNyoe', 3);

commit;