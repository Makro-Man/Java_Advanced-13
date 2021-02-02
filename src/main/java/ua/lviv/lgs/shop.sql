DROP DATABASE IF exists shop;
CREATE DATABASE shop CHAR SET utf8;
use shop;
CREATE TABLE user(
                     id int not null auto_increment primary key,
                     first_name VARCHAR(120) not null,
                     last_name VARCHAR(120) not null,
                     email VARCHAR(120) not null,
                     role VARCHAR(120)
);

CREATE TABLE product(
                        id int not null auto_increment primary key,
                        product_name VARCHAR(120) not null,
                        description VARCHAR(120) not null,
                        price double not null
);

CREATE TABLE bucket(
                       id int not null auto_increment primary key,
                       user_id int not null,
                       product_id int not null,
                       purchase_date timestamp
);

ALTER TABLE bucket ADD FOREIGN KEY (user_id) references user(id);
ALTER TABLE bucket ADD FOREIGN KEY (product_id) references product(id);

select * from user;
