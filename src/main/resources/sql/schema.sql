create table app_user (
    id integer primary key,
    username varchar(255) unique not null,
    password varchar(255) not null,
    role varchar(50) not null
);

-- user_pass = $2a$10$AGUk1lCUMYpEO4QKYodnc.J/QFudQx8sFuBthjyVC7CHXryia1qL6
-- admin_pass = $2a$10$e5sE2onHrad0wC2G1e.qGepU6JvxTYA4ZTtzIG2IWB2cRR3oC3VHS

insert into app_user (id, username, password, role) values (1, 'user', '$2a$10$AGUk1lCUMYpEO4QKYodnc.J/QFudQx8sFuBthjyVC7CHXryia1qL6', 'ROLE_USER');
insert into app_user (id, username, password, role) values (2, 'user', '$2a$10$e5sE2onHrad0wC2G1e.qGepU6JvxTYA4ZTtzIG2IWB2cRR3oC3VHS', 'ROLE_ADMIN');