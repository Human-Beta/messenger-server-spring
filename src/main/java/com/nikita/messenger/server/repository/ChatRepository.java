package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findAllByUsersId(long id, Pageable pageable);

    List<Chat> findAllByUsersIdAndIdNotIn(long id, List<Long> ids, Pageable pageable);

    @Query("SELECT count(c) = 1 FROM Chat c INNER JOIN c.users u WHERE c.id=:chatId AND u.id=:userId")
    boolean isUserInChat(long userId, long chatId);
}
