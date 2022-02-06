delete from user_role;
delete from usr;

insert into usr (id, username, password, active) values
    (1, 'user', '$2a$08$659wP7MAqy516bR8q9/9EejRe/F.8H./glwqSmK/LalIPpovv/Qaq', true),
    (2, 'tom', '$2a$08$659wP7MAqy516bR8q9/9EejRe/F.8H./glwqSmK/LalIPpovv/Qaq', true);

insert into user_role (user_id, roles) values
    (1, 'USER'), (1, 'ADMIN'), (2, 'USER');