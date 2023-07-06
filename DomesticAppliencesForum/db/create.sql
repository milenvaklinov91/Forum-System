create table tag_types
(
    tag_type_id int auto_increment
        primary key,
    type        varchar(45) null
);

create table users
(
    user_id           int auto_increment
        primary key,
    email             varchar(45) not null,
    password          varchar(45) not null,
    username          varchar(45) not null,
    first_name        varchar(32) not null,
    last_name         varchar(32) not null,
    is_admin          tinyint(1)  not null,
    registration_date datetime    null
);

create table admins
(
    admin_id int auto_increment
        primary key,
    phone    varchar(20) null,
    user_id  int         not null,
    constraint admins_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table posts
(
    post_id     int auto_increment
        primary key,
    user_id     int           not null,
    title       varchar(64)   not null,
    content     varchar(8192) not null,
    create_date datetime      not null,
    constraint posts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table comments
(
    comment_id  int auto_increment
        primary key,
    user_id     int           not null,
    comment     varchar(8192) not null,
    post_id     int           not null,
    create_date datetime      not null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table tags
(
    tag_id  int auto_increment
        primary key,
    post_id int not null,
    user_id int null,
    type    int not null,
    constraint tags_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint tags_tag_types_tag_type_id_fk
        foreign key (type) references tag_types (tag_type_id),
    constraint tags_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create index users_roles_role_id_fk
    on users (is_admin);

create table vote_types
(
    vote_type_id int auto_increment
        primary key,
    type         varchar(45) not null
);

create table votes
(
    vote_id    int auto_increment
        primary key,
    comment_id int not null,
    user_id    int null,
    post_id    int not null,
    type       int not null,
    constraint likes_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint votes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint votes_vote_types_vote_type_id_fk
        foreign key (type) references vote_types (vote_type_id)
);


