package com.nikita.messenger.server.service.impl;


import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.lang.Boolean.TRUE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    static final String NICKNAME = "nickname";

    @Spy
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserDetails userDetails;

    final User user = new User();

    void mockGetCurrentUser() {
        when(userDetails.getUsername()).thenReturn(NICKNAME);
        doReturn(userDetails).when(userService).getCurrentUserDetails();
        when(userRepository.findByNickname(NICKNAME)).thenReturn(of(user));
    }

    @Test
    void shouldReturnCurrentUserWhenGetCurrentUser() {
        mockGetCurrentUser();

        final User currentUser = userService.getCurrentUser();

        assertThat(currentUser).isSameAs(user);
    }

    @Test
    void shouldThrowExceptionWhenGetCurrentUserAndUsernameDoesNotExist() {
        mockGetCurrentUser();
        when(userRepository.findByNickname(NICKNAME)).thenReturn(empty());

        assertThatThrownBy(() -> userService.getCurrentUser())
                .hasMessage("There is no user with nickname: " + NICKNAME)
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void shouldReturnUserWhenGetUserByNickname() {
        when(userRepository.findByNickname(NICKNAME)).thenReturn(of(user));

        final User user = userService.getUserByNickname(NICKNAME);

        assertThat(user).isSameAs(this.user);
    }

    @Test
    void shouldThrowExceptionWhenGetUserByNicknameAndUsernameDoesNotExist() {
        assertThatThrownBy(() -> userService.getUserByNickname("does_not_exist"))
                .hasMessage("There is no user with nickname: does_not_exist")
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void shouldSaveUserWhenSave() {
        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    void shouldReturnTrueWhenExistsByNicknameAndUserExists() {
        when(userRepository.existsByNickname(NICKNAME)).thenReturn(TRUE);

        final boolean exists = userService.existsByNickname(NICKNAME);

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenExistsByNicknameAndUserDoesNotExist() {
        final boolean exists = userService.existsByNickname(NICKNAME);

        assertThat(exists).isFalse();
    }
}
