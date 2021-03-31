CREATE TABLE IF NOT EXISTS  users
(
    userid BIGSERIAL PRIMARY KEY ,
    username VARCHAR NOT NULL ,
    password VARCHAR NOT NULL ,
    enabled BOOLEAN,
    role VARCHAR not NULL
);

CREATE TABLE IF NOT EXISTS currency
(
    id BIGSERIAL PRIMARY KEY ,
    valute_id INT NOT NULL ,
    char_code VARCHAR NOT NULL ,
    valutename VARCHAR NOT NULL ,
    value int NOT NULL ,
    date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS history
(
    id BIGSERIAL PRIMARY KEY ,
    out_valute VARCHAR NOT NULL ,
    user_name VARCHAR NOT NULL,
    in_valute VARCHAR NOT NULL ,
    out_value INT NOT NULL,
    in_value INT NOT NULL ,
    currency FLOAT NOT NULL,
    date DATE NOT NULL
);
