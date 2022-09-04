CREATE TABLE users (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR (64) NOT NULL,
    avatar_url VARCHAR(64),

    PRIMARY KEY (id)
);

CREATE TABLE chat_types (
    id INTEGER NOT NULL,
    name VARCHAR(10) NOT NULL UNIQUE,

    PRIMARY KEY (id)
);

CREATE TABLE chats (
    id INTEGER NOT NULL AUTO_INCREMENT,
    type_id INTEGER NOT NULL,

    FOREIGN KEY (type_id) REFERENCES chat_types(id)
        ON DELETE RESTRICT,
    PRIMARY KEY (id)
);

CREATE TABLE chats_users (
    chat_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,

    FOREIGN KEY (chat_id) REFERENCES chats(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    PRIMARY KEY (chat_id, user_id)
);

CREATE TABLE messages (
    id INTEGER NOT NULL AUTO_INCREMENT,
    local_id INTEGER NOT NULL,
    chat_id INTEGER NOT NULL,
    sender_id INTEGER NOT NULL,
    value TEXT NOT NULL,
    date TIMESTAMP(4) NOT NULL,

    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (chat_id) REFERENCES chats(id),
    PRIMARY KEY (id)
);

CREATE TABLE oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256),
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(256)
);

CREATE TABLE oauth_access_token (
    authentication_id varchar(255) NOT NULL PRIMARY KEY,
    token_id varchar(255) NOT NULL,
    token blob NOT NULL,
    user_name varchar(255) NOT NULL,
    client_id varchar(255) NOT NULL,
    authentication blob NOT NULL,
    refresh_token varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE oauth_refresh_token (
    token_id varchar(255) NOT NULL,
    token blob NOT NULL,
    authentication blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
