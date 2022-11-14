package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.enums.ChatType;
import com.nikita.messenger.server.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findAllByUsersId(long id);

    List<Chat> findAllByUsersIdAndType(long id, ChatType chatType);

    List<Chat> findAllByUsersIdAndIdNotIn(long id, List<Long> ids, Pageable pageable);

    @Query("SELECT COUNT(c) = 1 FROM Chat c INNER JOIN c.users u WHERE c.id=:chatId AND u.id=:userId")
    boolean isUserInChat(long userId, long chatId);

    @Query("SELECT COUNT(c) = 1 FROM Chat c WHERE c.type='PRIVATE' " +
            "AND :userId1 IN (SELECT id FROM c.users) " +
            "AND :userId2 IN (SELECT id FROM c.users)")
    boolean privateChatExistsForUsers(long userId1, long userId2);
}
