package com.nikita.messenger.server.dao.impl;

import com.nikita.messenger.server.dao.MessageDAO;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.List.of;
import static java.util.Optional.empty;

@Component
public class MessageDAOImpl implements MessageDAO {
//    TODO: remove
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);

//    TODO: remove
    private Map<Long, List<Message>> messagesMap;

    @PostConstruct
    private void init() throws ParseException {
        final User me = createUser(0, "nikita_pobrito", "0", "Никита", "#");

        final User oleg = createUser(1, "oleg_ubiyca", "1", "Олег", "#");
        final User kotyah = createUser(2, "kitty", "2", "Котях", "#");
        final User vasya = createUser(3, "vasya_voin", "3", "Вася", "#");
        final User petya = createUser(4, "pushka_petrushka", "4", "Петя", "#");

        messagesMap = Map.of(
                1L, of(
                        createMessage(123, oleg, "Привет!", toDate("2022-06-12T08:23:00+03:00")),
                        createMessage(312, me, "Хэлооооу", toDate("2022-06-12T11:02:00+03:00")),
                        createMessage(124, oleg, "Как дела?", toDate("2022-06-12T12:24:00+03:00")),
                        createMessage(125, oleg, "еще не родила??", toDate("2022-06-12T12:25:00+03:00"))
                ),
                2L, of(
                        createMessage(4616, kotyah, "Ну че, как оно?", toDate("2022-06-11T08:23:00+03:00")),
                        createMessage(94736, me, "Та норм", toDate("2022-06-11T11:02:00+03:00")),
                        createMessage(1429, kotyah, "круто", toDate("2022-06-11T12:24:00+03:00"))
                ),
                3L, of(
                        createMessage(56111, vasya, "Як умру", toDate("2022-02-12T08:23:00+03:00")),
                        createMessage(472522, me, "То поховайте", toDate("2022-02-12T11:02:00+03:00")),
                        createMessage(3332, me, "мэнэ у могыли", toDate("2022-02-12T12:24:00+03:00"))
                ),
                4L, of(
                        createMessage(1234, petya, "Чувак это рэпчик", toDate("2021-06-12T08:23:00+03:00")),
                        createMessage(5432, me, "Круто круто", toDate("2021-06-12T11:02:00+03:00")),
                        createMessage(9951, petya, "действительно круто", toDate("2021-06-12T12:24:00+03:00"))
                )
        );
    }

    private User createUser(final long id, final String nickName, final String password, final String name,
                            final String avatarUrl) {
        final User user = new User();

        user.setId(id);
        user.setNickName(nickName);
        user.setPassword(password);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);

        return user;
    }

    private Message createMessage(final long id, final User sender, final String value, final Date date) {
        final Message message = new Message();

        message.setId(id);
        message.setValue(value);
        message.setSender(sender);
        message.setDate(date);

        return message;
    }

    private Date toDate(final String dateInString) throws ParseException {
        return formatter.parse(dateInString);
    }

    @Override
    public Optional<Message> getLastMessageFromChat(final long chatId) {
        Optional<Message> lastMessage = empty();

        final List<Message> messages = messagesMap.get(chatId);
        final int size = messages.size();

        if (size > 0) {
            lastMessage = Optional.of(messages.get(size - 1));
        }

        return lastMessage;
    }

    @Override
    public List<Message> getMessagesFromChat(final long chatId) {
        return messagesMap.get(chatId);
    }
}
