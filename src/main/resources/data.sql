SET NAMES utf8;

INSERT INTO oauth_client_details
    (
        client_id,
        client_secret,
        scope,
        authorized_grant_types,
        access_token_validity,
        refresh_token_validity,
        autoapprove
    )
VALUES
    (
        "test_id",
        "$2a$10$zM7fxyYGp3C8TOacd5Luku6PR8uSXq7iLu3GY4W3JIMZVsRTtX2N2",
        "basic",
        "password,refresh_token",
        3600,
        86400,
        true
    );

INSERT INTO users (id, nickname, password, name, avatar_url) VALUES
    (1, "nikita", "123", "Никита Шишов", "/nikita/0"),
    (2, "oleg_ubiyca", "123", "Олег Убийцын", "/oleg_ubiyca/0"),
    (3, "kitty", "123", "Котенок Муров", "/kitty/0"),
    (4, "vasya_voin", "123", "Василий Воинов", "/vasya_voin/0"),
    (5, "pushka_petrushka", "123", "Пётр Петрушев", "/pushka_petrushka/0");

INSERT INTO chat_types (id, name) VALUES
    (0, "PRIVATE"),
    (1, "GROUP"),
    (2, "CHANNEL");

INSERT INTO chats (id, type_id) VALUES
    (1, 0),
    (2, 0),
    (3, 0),
    (4, 0);

INSERT INTO chats_users (chat_id, user_id) VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 3),
    (3, 1),
    (3, 4),
    (4, 1),
    (4, 5);

INSERT INTO messages (localId, chatId, senderId, value, date) VALUES
--  Nikita and Oleg's messages
    (1, 1, 2, "Привет!", "2022-06-12 08:23:00"),
    (2, 1, 1, "Хэлооооу", "2022-06-12 11:02:00"),
    (3, 1, 2, "Как дела?", "2022-06-12 12:24:00"),
    (4, 1, 2, "еще не родила??", "2022-07-14 20:25:00"),

--  Nikita and Kitty's messages
    (1, 2, 3, "Очень у меня к тебе важный вопрос! Давно хотел спросить, но чето стеснялся.\n И вот все-таки решился и спрошу щас. Так вот, ну че, как оно? ", "2022-06-11 08:23:00"),
    (2, 2, 1, "Та норм", "2022-06-11 11:02:00"),
    (3, 2, 3, "круто", "2022-06-11 12:24:00"),

--  Nikita and Vasya's messages
    (1, 3, 4, "Як умру", "2022-02-12 08:23:00"),
    (2, 3, 1, "То поховайте", "2022-02-12 11:02:00"),
    (3, 3, 1, "мэнэ у могыли", "2022-02-12 12:24:00"),

--  Nikita and Petr's messages
    (1, 4, 5, "Чувак это рэпчик", "2021-06-12 08:23:00"),
    (2, 4, 1, "Круто круто", "2021-06-12 11:02:00"),
    (3, 4, 5, "действительно круто", "2021-06-12 12:24:00");
