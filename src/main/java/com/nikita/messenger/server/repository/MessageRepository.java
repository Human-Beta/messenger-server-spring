package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.model.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE chat_id = :chatId AND date < :sinceDate")
    List<Message> findAllByChatId(@Param("chatId") long chatId, @Param("sinceDate") Date sinceDate,
                                  PageRequest pageable);
}
