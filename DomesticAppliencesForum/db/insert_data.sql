insert into forum_system.users(username, password, email, first_name, last_name, registration_date, is_admin, is_blocked, user_login_id)
values ('milenvaklinov','milen1991','milenvaklinov@abv.bg', 1, 'Milen', 'Vaklinov', 1, now()),
       ('ledayovkova','leda1991','ledayovkova@abv.bg',2, 'Leda', 'Yovkova', 0, now()),
       ('kaloyanstanev','kaloyan1991','kaloyanstanev@abv.bg', 3, 'Kaloyan', 'Stanev', 0, now());

insert into forum_system.posts(user_id, title, content, create_date,tag_type_id)
values (1,'title','content',now(),1),
       (2,'title','content',now(),2),
       (3,'title','content',now(),3);

insert into forum_system.comments(user_id, comment, post_id, create_date)
values (1,'comment',1,now()),
       (2,'comment',2,now()),
       (3,'comment',3,now());

insert into forum_system.phone_number(user_id, phone_number)
values (1,'0888123456');

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