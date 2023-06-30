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

create table roles
(
    role_id int auto_increment
        primary key,
    role    varchar(45) not null
);

create table administrators
(
    admin_id     int auto_increment
        primary key,
    role_id      int     not null,
    phone_number int(20) not null,
    constraint administrators_roles_role_id_fk
        foreign key (role_id) references roles (role_id)
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
    role              int         not null,
    post              int         not null,
    registration_date datetime    null,
    constraint users_posts_post_id_fk
        foreign key (post) references posts (post_id),
    constraint users_roles_role_id_fk
        foreign key (role) references roles (role_id)
);

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


