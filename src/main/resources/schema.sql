CREATE TABLE users (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    name VARCHAR (64) NOT NULL,
    avatar_url VARCHAR(64),

    PRIMARY KEY (id)
);

CREATE TABLE chat_types (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,

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
    localId INTEGER NOT NULL,
    chatId INTEGER NOT NULL,
    senderId INTEGER NOT NULL,
    value TEXT NOT NULL,
    date TIMESTAMP NOT NULL,

    FOREIGN KEY (senderId) REFERENCES users(id),
    FOREIGN KEY (chatId) REFERENCES chats(id),
    PRIMARY KEY (id)
);
