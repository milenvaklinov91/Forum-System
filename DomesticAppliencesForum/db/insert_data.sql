insert into forum_system.user_login(username, password)
values ('milenvaklinov','milen1991'),
       ('ledayovkova','leda1991'),
       ('kaloyanstanev','kaloyan1991');

insert into forum_system.users(email,user_login_id, first_name, last_name, is_admin, registration_date)
values ('milenvaklinov@abv.bg', 1, 'Milen', 'Vaklinov', 1, now()),
       ('ledayovkova@abv.bg',2, 'Leda', 'Yovkova', 0, now()),
       ('kaloyanstanev@abv.bg', 3, 'Kaloyan', 'Stanev', 0, now());

insert into forum_system.posts(user_id, title, content, create_date,tag_type_id)
values (1,'title','content',now(),1),
       (2,'title','content',now(),2),
       (3,'title','content',now(),3);

insert into forum_system.comments(user_id, comment, post_id, create_date)
values (1,'comment',1,now()),
       (2,'comment',2,now()),
       (3,'comment',3,now());

insert into forum_system.admins(phone, user_id)
values ('0888123456',1);

insert into forum_system.tag_types(type)
values ('type'),
       ('type1'),
       ('type2');

insert into forum_system.vote_types(type)
values ('type'),
       ('type');

insert into forum_system.votes(comment_id, user_id, post_id, type)
values (1,1,1,1),
       (1,1,1,2);