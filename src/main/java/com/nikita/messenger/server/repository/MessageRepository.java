package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.model.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    Optional<Message> findFirstByChatIdOrderByDateDesc(long chatId);

    List<Message> findAllByChatIdOrderByDateDesc(long chatId, PageRequest pageable);
}
