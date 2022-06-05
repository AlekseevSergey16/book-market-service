create table author (
    id bigserial primary key,
    first_name varchar(255),
    last_name varchar(255),
    middle_name varchar(255)
);

create table publisher (
    id bigserial primary key,
    name varchar(255),
    information varchar(255),
    email varchar(255),
    phone varchar(255)
);

create table genre (
    id bigserial primary key,
    name varchar(255)
);

create table book (
    id bigserial primary key,
    title varchar(255),
    description varchar(255),
    publication_year int,
    cost decimal,
    pages int,
    weight int,
    genre_id bigint,
    publisher_id bigint,
    foreign key (publisher_id) references publisher (id),
    foreign key (genre_id) references genre (id)
);

create table book_author (
    book_id bigint,
    author_id bigint,
    foreign key (book_id) references book (id),
    foreign key (author_id) references author (id)
);

create table orders (
    id bigserial primary key,
    order_date timestamp,
    total_cost decimal,
    user_id bigint,
    foreign key (user_id) references users(id)
);

create table order_item (
    id bigserial primary key,
    order_id bigint,
    book_id bigint,
    quantity int,
    foreign key (order_id) references orders (id),
    foreign key (book_id) references book (id)
);

create table shipment (
    id bigserial primary key,
    shipment_date date,
    amount int,
    book_id bigint,
    foreign key (book_id) references book (id)
);

CREATE TABLE shipment (
    id bigserial primary key,
    shipment_date date,
    supplier_id int,
    totalAmount int,
    foreign key (supplier_id) references supplier (id)
);

create table shipment_item (
    id bigserial primary key,
    shipment_id bigint,
    book_id bigint,
    amount int,
    UNIQUE (shipment_id, book_id),
    foreign key (shipment_id) references shipment (id),
    foreign key (book_id) references book (id)
);

create table supplier (
    id bigserial primary key,
    name varchar(255)
);

CREATE TABLE storage (
    shipment_id bigint,
    book_id bigint,
    amount int,
    foreign key (shipment_id) references shipment (id),
    foreign key (book_id) references book (id),
    primary key(shipment_id, book_id)
);

create table order_item (
    id bigserial primary key,
    order_id bigint,
    book_id bigint,
    quantity int,
    foreign key (order_id) references orders (id),
    foreign key (book_id) references book (id)
);

create table users (
    id bigserial primary key,
    username varchar(255) not null,
    password varchar(255) not null
);


