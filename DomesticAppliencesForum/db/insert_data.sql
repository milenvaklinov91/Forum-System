insert into forum_system.users(email, password, username, first_name, last_name, is_admin, registration_date)
values ('milenvaklinov@abv.bg', 'milen1991', 'milenvaklinov', 'Milen', 'Vaklinov', 1, current_date),
       ('ledayovkova@abv.bg', 'leda1991', 'ledayovkova', 'Leda', 'Yovkova', 0, current_date),
       ('kaloyanstanev@abv.bg', 'kaloyan1991', 'kaloyanstanev', 'Kaloyan', 'Stanev', 0, current_date);

insert into forum_system.posts(user_id, title, content, create_date)
values (1,'title','content',current_date),
       (2,'title','content',current_date),
       (3,'title','content',current_date);


insert into forum_system.comments(user_id, comment, post_id, create_date)
values (1,'comment',1,current_date),
       (2,'comment',2,current_date),
       (3,'comment',3,current_date);

insert into forum_system.admins(phone, user_id)
values ('0888123456',1);

insert into forum_system.tag_types(type)
values ('type'),
       ('type1'),
       ('type2');

insert into forum_system.tags(post_id, user_id, type)
values (1,1,1),
       (2,2,2),
       (3,3,3);

insert into forum_system.vote_types(type)
values ('type'),
       ('type');

insert into forum_system.votes(comment_id, user_id, post_id, type)
values (1,1,1,1),
       (1,1,1,2);

