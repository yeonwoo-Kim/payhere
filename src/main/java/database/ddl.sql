create table account_books (
       id bigint not null auto_increment,
        amount bigint,
        is_deleted bit,
        memo varchar(255),
        update_date datetime(6),
        use_date datetime(6),
        user_id bigint,
        primary key (id)
    )

create table users (
       id bigint not null auto_increment,
        email varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    )

alter table users
       add constraint user_unique_email unique (email)

alter table account_books
       add constraint account_books_fk_email
       foreign key (user_id)
       references users (id)