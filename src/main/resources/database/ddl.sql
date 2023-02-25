create table author (
    id bigserial primary key,
    first_name varchar(255) NOT NULL ,
    last_name varchar(255) NOT NULL,
    middle_name varchar(255)
);

create table publisher (
    id bigserial primary key,
    name varchar(255) NOT NULL,
    information varchar(255),
    email varchar(255) NOT NULL,
    phone varchar(255) NOT NULL
);

create table genre (
    id bigserial primary key,
    name varchar(255) NOT NULL
);

create table book (
    id bigserial primary key,
    title varchar(255) NOT NULL,
    description varchar(255),
    publication_year int NOT NULL,
    cost decimal NOT NULL,
    pages int NOT NULL,
    weight int NOT NULL,
    genre_id bigint NOT NULL,
    publisher_id bigint NOT NULL,
    foreign key (publisher_id) references publisher (id),
    foreign key (genre_id) references genre (id)
);

create table book_author (
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    foreign key (book_id) references book (id),
    foreign key (author_id) references author (id),
    primary key (book_id, author_id)
);

create table orders (
    id bigserial primary key,
    order_date timestamp NOT NULL,
    total_cost decimal NOT NULL,
    user_id bigint,
    foreign key (user_id) references users(id)
);

CREATE TABLE shipment (
    id bigserial primary key,
    shipment_date date NOT NULL,
    supplier_id int NOT NULL,
    totalAmount int NOT NULL,
    foreign key (supplier_id) references supplier (id)
);

create table shipment_item (
    id bigserial primary key,
    shipment_id bigint NOT NULL,
    book_id bigint NOT NULL,
    amount int NOT NULL,
    UNIQUE (shipment_id, book_id),
    foreign key (shipment_id) references shipment (id),
    foreign key (book_id) references book (id)
);

create table supplier (
    id bigserial primary key,
    name varchar(255) NOT NULL
);

create table order_item (
    id bigserial primary key,
    order_id bigint NOT NULL,
    book_id bigint NOT NULL,
    quantity int NOT NULL,
    foreign key (order_id) references orders (id),
    foreign key (book_id) references book (id)
);

create table users (
    id bigserial primary key,
    username varchar(255) not null unique ,
    password varchar(255) not null,
    role varchar(50) not null
);


