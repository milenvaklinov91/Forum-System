create schema forum_system;

create table forum_system.tag_types
(
    tag_type_id int auto_increment
        primary key,
    type        varchar(45) null
);

create table forum_system.users
(
    user_id           int auto_increment
        primary key,
    username          varchar(45) not null,
    password          varchar(45) not null,
    email             varchar(45) not null,
    first_name        varchar(32) not null,
    last_name         varchar(32) not null,
    registration_date datetime    null,
    is_admin          tinyint(1)  not null,
    is_blocked        tinyint(1)  not null
);

create table forum_system.phone_number
(
    phone_number_id int auto_increment
        primary key,
    user_id         int         not null,
    phone_number    varchar(20) null,
    constraint phone_number_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table forum_system.posts
(
    post_id     int auto_increment
        primary key,
    title       varchar(64)   not null,
    content     varchar(8192) not null,
    create_date datetime      not null,
    user_id     int           not null,
    tag_type_id int           not null,
    constraint posts_tag_types_tag_type_id_fk
        foreign key (tag_type_id) references tag_types (tag_type_id),
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);


create table forum_system.comments
(
    comment_id  int auto_increment
        primary key,
    comment     varchar(8192) not null,
    create_date datetime      not null,
    user_id     int           not null,
    post_id     int           not null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id) on delete cascade,
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table forum_system.vote_types
(
    vote_type_id int auto_increment
        primary key,
    type         varchar(45) not null
);

create table forum_system.vote_comments
(
    vote_comment_id int auto_increment
        primary key,
    comment_id      int not null,
    user_id         int not null,
    vote_type_id    int not null,
    constraint vote_comments_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint vote_comments_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint vote_comments_vote_types_vote_type_id_fk
        foreign key (vote_type_id) references vote_types (vote_type_id)
);

create table forum_system.votes
(
    vote_id    int auto_increment
        primary key,
    user_id    int null,
    post_id    int not null,
    type       int not null,
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint votes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint votes_vote_types_type_fk
        foreign key (type) references vote_types (vote_type_id)
);