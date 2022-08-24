package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.model.Chat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c INNER JOIN c.users u WHERE u.id=:userId")
    List<Chat> findAllByUserId(@Param("userId") long userId, PageRequest pageable);

    @Query("SELECT count(c) = 1 FROM Chat c INNER JOIN c.users u WHERE c.id=:chatId AND u.id=:userId")
    boolean isUserInChat(@Param("userId") long userId, @Param("chatId") long chatId);
}
