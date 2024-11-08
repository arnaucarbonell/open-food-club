DROP TABLE if exists product;
CREATE TABLE product
(
    id VARCHAR (256) PRIMARY KEY ,
    name VARCHAR (256) ,
    price NUMERIC,
    measure_unit VARCHAR (256),
    provider VARCHAR (256),
    vat_type VARCHAR (100),
    category VARCHAR(256),
    initial_date DATE,
    day_of_week VARCHAR(20),
    num_of_periods INT,
    period VARCHAR(20),
    image CLOB
);

DROP TABLE if exists user;
CREATE TABLE user
(
    id VARCHAR (256) PRIMARY KEY ,
    name VARCHAR (30),
    secondname VARCHAR (30),
    email VARCHAR (100) ,
    password VARCHAR(70) NOT NULL DEFAULT '{bcrypt}$2a$10$fVKfcc47q6lrNbeXangjYeY000dmjdjkdBxEOilqhapuTO5ZH0co2',
    enabled TINYINT NOT NULL DEFAULT 1
);

DROP TABLE if exists orders;
CREATE TABLE orders
(
    id VARCHAR (256) PRIMARY KEY,
    end_date DATE,
    actual_state VARCHAR (300),
    user VARCHAR (256),
    FOREIGN KEY (user) REFERENCES user (id)
);

DROP TABLE if exists subscriptions;
CREATE TABLE subscriptions
(
    id VARCHAR (256) PRIMARY KEY ,
    amount NUMERIC,
    price NUMERIC,
    initial_date DATE,
    subscription_order VARCHAR (256),
    product VARCHAR (256),
    user VARCHAR (256),
    FOREIGN KEY (subscription_order) REFERENCES orders (id),
    FOREIGN KEY (user) REFERENCES user (id),
    FOREIGN KEY (product) REFERENCES product (id)
);

DROP TABLE if EXISTS authorities;
CREATE TABLE authorities
(
    authority_id int(11)     NOT NULL AUTO_INCREMENT,
    username     varchar(45) NOT NULL,
    role         varchar(45) NOT NULL,
    PRIMARY KEY (authority_id),
    UNIQUE KEY uni_username_role (role,username),
    CONSTRAINT fk_user_id FOREIGN KEY (username) REFERENCES user (id)
);
