create table public.author
(
    id          bigserial
        constraint author_pkey
            primary key,
    first_name  varchar(255),
    last_name   varchar(255),
    middle_name varchar(255),
    information varchar(255)
);

create table public.publisher
(
    id          bigserial
        constraint publisher_pkey
            primary key,
    name        varchar(255),
    information varchar,
    email       varchar(255),
    phone       varchar(255)
);

create table public.genre
(
    id   bigserial
        constraint genre_pkey
            primary key,
    name varchar(255)
);

create table public.book
(
    id               bigserial
        constraint book_pkey
            primary key,
    title            varchar(255),
    description      varchar,
    publication_year integer,
    cost             numeric,
    pages            integer,
    weight           integer,
    genre_id         bigint
        constraint book_genre_id_fkey
            references public.genre,
    publisher_id     bigint
        constraint book_publisher_id_fkey
            references public.publisher,
    amount           integer default 0
);

create table public.book_author
(
    book_id   bigint
        constraint book_author_book_id_fkey
            references public.book,
    author_id bigint
        constraint book_author_author_id_fkey
            references public.author
);

create table public.supplier
(
    id   bigserial
        constraint supplier_pkey
            primary key,
    name varchar(255)
);

create table public.shipment
(
    id            bigserial
        constraint shipment_pkey
            primary key,
    shipment_date date,
    supplier_id   integer
        constraint shipment_supplier_id_fkey
            references public.supplier,
    total_amount  integer
);
create table public.shipment_item
(
    id          bigserial
        constraint shipment_item_pkey
            primary key,
    shipment_id bigint
        constraint shipment_item_shipment_id_fkey
            references public.shipment,
    book_id     bigint
        constraint shipment_item_book_id_fkey
            references public.book,
    amount      integer,
    constraint shipment_item_shipment_id_book_id_key
        unique (shipment_id, book_id)
);

create table public.users
(
    id       bigserial
        constraint users_pkey
            primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    role     varchar(100)
);

create table public.orders
(
    id         bigserial
        constraint orders_pkey
            primary key,
    order_date timestamp,
    total_cost numeric,
    user_id    bigint
        constraint orders_users_id_fk
            references public.users
);

create table public.order_item
(
    id       bigserial
        constraint order_item_pkey
            primary key,
    order_id bigint
        constraint order_item_order_id_fkey
            references public.orders,
    book_id  bigint
        constraint order_item_book_id_fkey
            references public.book,
    quantity integer
);


