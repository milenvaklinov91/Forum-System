create table comments
(
    comment_id int auto_increment
        primary key,
    user_id    int           not null,
    comment    varchar(8192) not null
);

create table posts
(
    post_id     int auto_increment
        primary key,
    user_id     int           not null,
    title       varchar(64)   not null,
    content     varchar(8192) not null,
    comment     int           not null,
    `like`      int           null,
    create_date datetime      not null,
    constraint posts_comments_comment_id_fk
        foreign key (comment) references comments (comment_id)
);

create index posts_users_user_id_fk
    on posts (user_id);

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
    post              int         not null,
    registration_date datetime    null,
    constraint users_posts_post_id_fk
        foreign key (post) references posts (post_id)
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

create index users_roles_role_id_fk
    on users (is_admin);

create table vote_types
(
    vote_id int auto_increment
        primary key,
    type    varchar(6) not null
);

create table votes
(
    vote_id    int auto_increment
        primary key,
    comment_id int null,
    user_id    int null,
    type       int not null,
    constraint likes_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references users (user_id),
    constraint votes_vote_types_vote_id_fk
        foreign key (type) references vote_types (vote_id)
);


