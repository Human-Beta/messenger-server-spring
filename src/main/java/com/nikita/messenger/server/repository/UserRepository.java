package com.nikita.messenger.server.repository;

import com.nikita.messenger.server.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    List<User> findAllByNicknameStartingWithAndIdNotIn(String nickname, List<Long> excludedIds, Pageable pageable);
}
