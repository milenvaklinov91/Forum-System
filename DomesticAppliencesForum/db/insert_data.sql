insert into forum_system.admins (admin_id, phone, user_id)
values  (1, '0888123456', 1),
        (2, '0888123457', 2),
        (3, '0888123458', 3);

insert into forum_system.comments (comment_id, user_id, comment)
values  (1, 1, 'comment'),
        (2, 2, 'comment1'),
        (3, 3, 'comment2');

insert into forum_system.posts (post_id, user_id, title, content, comment, like, create_date)
values  (1, 1, 'title', 'content', 1, 1, '2023-07-03 14:31:27'),
        (2, 2, 'title1', 'content1', 2, 2, '2023-07-03 14:36:04'),
        (3, 3, 'title2', 'content2', 3, 3, '2023-07-03 14:46:29');

insert into forum_system.users (user_id, email, password, username, first_name, last_name, is_admin, post, registration_date)
values  (1, 'milenvaklinov@abv.bg', 'milen1991', 'milenvaklinov', 'Milen', 'Vaklinov', 0, 1, '2023-07-03 14:32:46'),
        (2, 'ledayovkova@abv.bg', 'leda1991', 'ledayovkova', 'Leda', 'Yovkova', 1, 2, '2023-07-03 14:37:14'),
        (3, 'kaloyanstanev@abv.bg', 'kaloyan1991', 'kaloyanstanev', 'Kaloyan', 'Stanev', 1, 3, '2023-07-03 14:48:01');

