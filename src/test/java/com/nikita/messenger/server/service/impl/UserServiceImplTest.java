package com.nikita.messenger.server.service.impl;


import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String NICKNAME = "nickname";
    private static final List<Long> EXCLUDED_IDS = List.of(42L);
    private static final int PAGE = 123;
    private static final int SIZE = 321;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetails userDetails;
    @Captor
    private ArgumentCaptor<Pageable> paginationCaptor;

    private final User user = new User();

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

    @Test
    void shouldReturnUsersByNicknameWhenGetUsersByNicknameExcludeIds() {
        when(userRepository.findAllByNicknameStartingWithAndIdNotIn(eq(NICKNAME), eq(EXCLUDED_IDS),
                                                                    any(Pageable.class))).thenReturn(List.of(user));

        final List<User> users = userService.getUsersByNicknameExcludeIds(NICKNAME, EXCLUDED_IDS, PAGE, SIZE);

        assertThat(users).containsExactly(user);
    }

    @Test
    void shouldPassPageableWithPageWhenGetUsersByNicknameExcludeIds() {
        userService.getUsersByNicknameExcludeIds(NICKNAME, EXCLUDED_IDS, PAGE, SIZE);

        verify(userRepository).findAllByNicknameStartingWithAndIdNotIn(eq(NICKNAME), eq(EXCLUDED_IDS),
                                                                      paginationCaptor.capture());

        final Pageable pagination = paginationCaptor.getValue();

        assertThat(pagination.getPageNumber()).isEqualTo(PAGE - 1);
    }

    @Test
    void shouldPassPageableWithSizeWhenGetUsersByNicknameExcludeIds() {
        userService.getUsersByNicknameExcludeIds(NICKNAME, EXCLUDED_IDS, PAGE, SIZE);

        verify(userRepository).findAllByNicknameStartingWithAndIdNotIn(eq(NICKNAME), eq(EXCLUDED_IDS),
                                                                      paginationCaptor.capture());

        final Pageable pagination = paginationCaptor.getValue();

        assertThat(pagination.getPageSize()).isEqualTo(SIZE);
    }

    @Test
    void shouldPassPageableWithSortDirectionWhenGetUsersByNicknameExcludeIds() {
        userService.getUsersByNicknameExcludeIds(NICKNAME, EXCLUDED_IDS, PAGE, SIZE);

        verify(userRepository).findAllByNicknameStartingWithAndIdNotIn(eq(NICKNAME), eq(EXCLUDED_IDS),
                                                                      paginationCaptor.capture());

        final Pageable pagination = paginationCaptor.getValue();

        assertThat(pagination.getSort().getOrderFor("id")).isNotNull()
                .returns(ASC, Sort.Order::getDirection);
    }
}
