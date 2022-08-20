package com.nikita.messenger.server.dao.impl;

import com.nikita.messenger.server.dao.ChatDAO;
import com.nikita.messenger.server.enums.ChatType;
import com.nikita.messenger.server.model.Chat;
import com.nikita.messenger.server.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nikita.messenger.server.enums.ChatType.PRIVATE;
import static java.util.List.of;

@Component
public class ChatDAOImpl implements ChatDAO {
//    TODO: remove
    private List<Chat> chats;

    @PostConstruct
    private void init() {
        final User me = createUser(0, "nikita", "0", "Никита", "#");

        final User oleg = createUser(1, "oleg_ubiyca", "1", "Олег", "#");
        final User kotyah = createUser(2, "kitty", "2", "Котях", "#");
        final User vasya = createUser(3, "vasya_voin", "3", "Вася", "#");
        final User petya = createUser(4, "pushka_petrushka", "4", "Петя", "#");

        chats = of(
                createChat(1, PRIVATE, of(me, oleg)),
                createChat(2, PRIVATE, of(me, kotyah)),
                createChat(3, PRIVATE, of(me, vasya)),
                createChat(4, PRIVATE, of(me, petya))
        );
    }

    private Chat createChat(final long id, final ChatType type, final List<User> users) {
        final Chat chat = new Chat();

        chat.setId(id);
        chat.setType(type);
        chat.setUsers(users);

        return chat;
    }

    private User createUser(final long id, final String nickname, final String password, final String name,
                            final String avatarUrl) {
        final User user = new User();

        user.setId(id);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);

        return user;
    }

    @Override
    public List<Chat> getChatsFor(final long userId, final int page, final int size) {
        return chats.stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Chat> getChat(final long chatId) {
        if (chats.size() < chatId) {
            return Optional.empty();
        }

        return Optional.of(chats.get((int) chatId - 1));
    }
}
