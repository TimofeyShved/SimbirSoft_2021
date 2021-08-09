--liquibase formatted sql

--changeset liquibase-demo-service:13

ALTER TABLE user_entity ADD email varchar(255);

ALTER TABLE user_entity ADD password varchar(255);

UPDATE user_entity
SET email = 'aaa@mail.ru', password= '$2a$04$DKrrteJZ9/SxNL9iwpeAXOljI4OsuktSG1LAhmbWapci92EyWcg/i'
WHERE user_id = 1;

UPDATE user_entity
SET email = 'bbb@mail.ru', password= '$2a$04$DKrrteJZ9/SxNL9iwpeAXOljI4OsuktSG1LAhmbWapci92EyWcg/i'
WHERE user_id = 2;

UPDATE user_entity
SET email = 'ccc@mail.ru', password= '$2a$04$DKrrteJZ9/SxNL9iwpeAXOljI4OsuktSG1LAhmbWapci92EyWcg/i23'
WHERE user_id = 3;

UPDATE user_entity
SET email = 'ddd@mail.ru', password= '$2a$04$DKrrteJZ9/SxNL9iwpeAXOljI4OsuktSG1LAhmbWapci92EyWcg/i'
WHERE user_id = 4;

UPDATE user_entity
SET email = 'fff@mail.ru', password= '$2a$04$DKrrteJZ9/SxNL9iwpeAXOljI4OsuktSG1LAhmbWapci92EyWcg/i'
WHERE user_id = 5;