insert into forum_system.users(username, password, email, first_name, last_name, registration_date, is_admin, is_blocked)
values ('milenvaklinov','milen1991','milenvaklinov@abv.bg', 'Milen', 'Vaklinov', now(),1,0),
       ('ledayovkova','leda1991','ledayovkova@abv.bg', 'Leda', 'Yovkova', now(),0,0),
       ('kaloyanstanev','kaloyan1991','kaloyanstanev@abv.bg', 'Kaloyan', 'Stanev', now(),0,0);

insert into forum_system.tag_types(type)
values ('type'),
       ('type1'),
       ('type2');

insert into forum_system.posts(title, content, create_date, user_id, tag_type_id)
values ('title','content',now(),1,1),
       ('title','content',now(),2,2),
       ('title','content',now(),3,3);

insert into forum_system.vote_types(type)
values ('type'),
       ('type');

insert into forum_system.votes(user_id, post_id, type)
values (1,1,1),
       (1,1,2);

insert into forum_system.phone_number(user_id, phone_number)
values (1,'0888123456');

insert into forum_system.comments(comment, create_date, user_id, post_id)
values ('comment',now(),1,1),
       ('comment',now(),2,2),
       ('comment',now(),2,2);

insert into forum_system.vote_comments(comment_id, user_id, vote_type_id)
values (1,1,1),
       (1,1,2);