insert into forum_system.users(username, password, email, first_name, last_name, registration_date, is_admin,
                               is_blocked)
values ('milenvaklinov', 'milen1991', 'milenvaklinov@abv.bg', 'Milen', 'Vaklinov', now(), 1, 0),
       ('ledayovkova', 'leda1991', 'ledayovkova@abv.bg', 'Leda', 'Yovkova', now(), 0, 0),
       ('kaloyanstanev', 'kaloyan1991', 'kaloyanstanev@abv.bg', 'Kaloyan', 'Stanev', now(), 0, 0),
       ('petrakovacheva', 'petra1992', 'petrakovacheva@abv.bg', 'Petra', 'Kovacheva', NOW(), 0, 0),
       ('georgimarinov', 'georgi1993', 'georgimarinov@abv.bg', 'Georgi', 'Marinov', NOW(), 0, 0),
       ('stefanadams', 'stefan1994', 'stefanadams@abv.bg', 'Stefan', 'Adams', NOW(), 0, 0),
       ('natalianikolova', 'natalia1995', 'natalianikolova@abv.bg', 'Natalia', 'Nikolova', NOW(), 0, 0),
       ('ivanmitrev', 'ivan1996', 'ivanmitrev@abv.bg', 'Ivan', 'Mitrev', NOW(), 0, 0),
       ('mariaangelova', 'maria1997', 'mariaangelova@abv.bg', 'Maria', 'Angelova', NOW(), 0, 0),
       ('georgitodorov', 'george1998', 'georgitodorov@abv.bg', 'Georgi', 'Todorov', NOW(), 0, 0),
       ('anastasiapetrova', 'anastasia1999', 'anastasiapetrova@abv.bg', 'Anastasia', 'Petrova', NOW(), 0, 0),
       ('dimitarchernev', 'dimitar2000', 'dimitarchernev@abv.bg', 'Dimitar', 'Chernev', NOW(), 0, 0),
       ('aleksandarivanov', 'aleksandar2001', 'aleksandarivanov@abv.bg', 'Aleksandar', 'Ivanov', NOW(), 0, 0);

insert into forum_system.tag_types(type)
values ('microwave'),
       ('coffee machine'),
       ('fridge'),
       ('toaster'),
       ('blender'),
       ('oven'),
       ('dishwasher'),
       ('kettle'),
       ('juicer'),
       ('food processor'),
       ('mixer'),
       ('slow cooker'),
       ('air fryer');

insert into forum_system.posts(title, content, create_date, user_id, tag_type_id)
values ('Problem with my microwave!!!', 'Hello everyone,can someone help me,my microwave is not work!!!', now(), 1, 1),
       ('Coffee machine isn''t work!', 'Hi,my coffee machine is old,but I love it,so can someone help my!', now(), 2,
        2),
       ('My fridge isn''t work!!!', 'Good morning,does anybody know someone who can repair my fridge?', now(), 3, 3),
       ('Need help with my toaster!',
        'Hello everyone, my toaster stopped working, and I need some advice on how to fix it.', NOW(), 4, 4),
       ('Blender not working properly',
        'Hi all, my blender is making strange noises. Any suggestions on how to troubleshoot it?', NOW(), 5, 5),
       ('Oven not heating up', 'Hey, my oven isn''t heating up as it should. Any tips on what might be wrong?', NOW(),
        6, 6),
       ('Dishwasher won''t start',
        'Hello, my dishwasher is not starting the cycle. Can someone help me figure out the issue?', NOW(), 7, 7),
       ('Kettle leaking water', 'Hi everyone, my kettle is leaking water from the bottom. How can I fix this?', NOW(),
        8, 8),
       ('Juicer not extracting juice',
        'Hello, my juicer is not extracting juice properly. Any ideas on how to get it working again?', NOW(), 9, 9),
       ('Food processor blades stuck',
        'Hi all, the blades of my food processor are stuck. Any suggestions on how to free them?', NOW(), 10, 10),
       ('Mixer making strange noises', 'Hello, my mixer is making weird noises. Should I be concerned?', NOW(), 11, 11),
       ('Slow cooker not heating', 'Hi, my slow cooker isn''t heating up. Any tips on diagnosing and fixing the issue?',
        NOW(), 12, 12),
       ('Air fryer not turning on',
        'Hello everyone, my air fryer is not turning on. Any advice on how to troubleshoot it?', NOW(), 13, 13);

insert into forum_system.vote_types(type)
values ('like'),
       ('dislike');

insert into forum_system.votes(user_id, post_id, type)
values (1, 1, 1),
       (1, 1, 2);

insert into forum_system.phone_number(user_id, phone_number)
values (1, '0888123456');

insert into forum_system.comments(comment, create_date, user_id, post_id)
values ('I can help you,to fix it!!!', now(), 1, 1),
       ('Yes,I know where there is a service station!', now(), 2, 2),
       ('Hello,what is tre problem,give me more information,please!!!', now(), 2, 2),
       ('Sure, I can provide some troubleshooting steps for you!', NOW(), 3, 3),
       ('I had a similar issue with mine. Let me share what I did to fix it.', NOW(), 4, 4),
       ('I am sorry to hear that. Is it still under warranty?', NOW(), 5, 5),
       ('Do you have the model number of your microwave?', NOW(), 6, 6),
       ('If you need any help, feel free to ask!', NOW(), 7, 7),
       ('I can recommend a reliable repair service for you.', NOW(), 8, 8),
       ('Please check the power cord and the fuse.', NOW(), 9, 9),
       ('Have you tried cleaning the coffee machine thoroughly?', NOW(), 10, 10),
       ('Make sure the water reservoir is properly seated.', NOW(), 11, 11),
       ('Have you descaled the coffee machine recently?', NOW(), 12, 12);

insert into forum_system.vote_comments(comment_id, user_id, vote_type_id)
values (1, 1, 1),
       (1, 1, 2);