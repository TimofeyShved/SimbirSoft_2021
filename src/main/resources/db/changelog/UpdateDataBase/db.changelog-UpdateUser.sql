--liquibase formatted sql

--changeset liquibase-demo-service:add-user-name-constraint

ALTER TABLE user_entity ADD email varchar(255);

ALTER TABLE user_entity ADD password varchar(255);

UPDATE user_entity
SET email = 'aaa@mail.ru', password= '123'
WHERE user_id = 1;

UPDATE user_entity
SET email = 'bbb@mail.ru', password= '123'
WHERE user_id = 2;

UPDATE user_entity
SET email = 'ccc@mail.ru', password= '123'
WHERE user_id = 3;

UPDATE user_entity
SET email = 'ddd@mail.ru', password= '123'
WHERE user_id = 4;

UPDATE user_entity
SET email = 'fff@mail.ru', password= '123'
WHERE user_id = 5;