package com.nikita.messenger.server.dao.impl;

import com.nikita.messenger.server.dao.MessageDAO;
import com.nikita.messenger.server.model.Message;
import com.nikita.messenger.server.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Component
public class MessageDAOImpl implements MessageDAO {
    //    TODO: remove
    private long id = 0;

//    TODO: remove
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH);

//    TODO: remove
    private Map<Long, List<Message>> messagesMap;

    @PostConstruct
    private void init() throws ParseException {
        final User me = createUser(0, "nikita", "0", "Никита", "#");

        final User oleg = createUser(1, "oleg_ubiyca", "1", "Олег", "#");
        final User kotyah = createUser(2, "kitty", "2", "Котях", "#");
        final User vasya = createUser(3, "vasya_voin", "3", "Вася", "#");
        final User petya = createUser(4, "pushka_petrushka", "4", "Петя", "#");

        messagesMap = Map.of(
                1L, new ArrayList<>(Arrays.asList(
                        createMessage(++id, oleg.getId(), 1L, "Привет!", toDate("2022-06-12T08:23:00+03:00")),
                        createMessage(++id, me.getId(), 1L, "Хэлооооу", toDate("2022-06-12T11:02:00+03:00")),
                        createMessage(++id, oleg.getId(), 1L, "Как дела?", toDate("2022-06-12T12:24:00+03:00")),
                        createMessage(++id, oleg.getId(), 1L, "еще не родила??", toDate("2022-07-14T20:25:00+03:00"))
                )),
                2L, new ArrayList<>(Arrays.asList(
                        createMessage(++id, kotyah.getId(), 2L, "Очень у меня к тебе важный вопрос! Давно хотел спросить, но чето стеснялся." + System.lineSeparator() + " И вот все-таки решился и спрошу щас. Так вот, ну че, как оно? ", toDate("2022-06-11T08:23:00+03:00")),
                        createMessage(++id, me.getId(), 2L, "Та норм", toDate("2022-06-11T11:02:00+03:00")),
                        createMessage(++id, kotyah.getId(), 2L, "круто", toDate("2022-06-11T12:24:00+03:00"))
                )),
                3L, new ArrayList<>(Arrays.asList(
                        createMessage(++id, vasya.getId(), 3L, "Як умру", toDate("2022-02-12T08:23:00+03:00")),
                        createMessage(++id, me.getId(), 3L, "То поховайте", toDate("2022-02-12T11:02:00+03:00")),
                        createMessage(++id, me.getId(), 3L, "мэнэ у могыли", toDate("2022-02-12T12:24:00+03:00"))
                )),
                4L, new ArrayList<>(Arrays.asList(
                        createMessage(++id, petya.getId(), 4L, "Чувак это рэпчик", toDate("2021-06-12T08:23:00+03:00")),
                        createMessage(++id, me.getId(), 4L, "Круто круто", toDate("2021-06-12T11:02:00+03:00")),
                        createMessage(++id, petya.getId(), 4L, "действительно круто", toDate("2021-06-12T12:24:00+03:00"))
                ))
        );
    }

    private User createUser(final long id, final String nickName, final String password, final String name,
                            final String avatarUrl) {
        final User user = new User();

        user.setId(id);
        user.setNickname(nickName);
        user.setPassword(password);
        user.setName(name);
        user.setAvatarUrl(avatarUrl);

        return user;
    }

    private Message createMessage(final long id, final long senderId, final long chatId, final String value, final Date date) {
        final Message message = new Message();

        message.setId(id);
        message.setValue(value);
        message.setSenderId(senderId);
        message.setChatId(chatId);
        message.setDate(date);

        return message;
    }

    private Date toDate(final String dateInString) throws ParseException {
        return formatter.parse(dateInString);
    }

    @Override
    public Optional<Message> getMessage(final long messageId) {
        return messagesMap.values().stream()
                .flatMap(Collection::stream)
                .filter(message -> Objects.equals(message.getId(), messageId))
                .findFirst();
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
    public List<Message> getMessagesFromChat(final long chatId, final int page, final int size) {
        return messagesMap.get(chatId).stream()
//                TODO: sort by date descending
                .sorted(((chat1, chat2) -> chat2.getDate().compareTo(chat1.getDate())))
                .skip((long) (page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public long putMessageToChat(final Message message) {
        messagesMap.get(message.getChatId())
                .add(message);

        message.setId(++id);

        return id;
    }
}
