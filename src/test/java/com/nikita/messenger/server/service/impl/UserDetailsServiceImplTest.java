package com.nikita.messenger.server.service.impl;

import com.nikita.messenger.server.model.User;
import com.nikita.messenger.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    static final String NICKNAME = "nickname";
    static final String PASSWORD = "password";
    static final String ROLE = "ROLE_USER";

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    final User user = new User();

    @BeforeEach
    void setUp() {
        when(userRepository.findByNickname(NICKNAME)).thenReturn(of(user));

        user.setNickname(NICKNAME);
        user.setPassword(PASSWORD);
    }

    @Test
    void shouldThrowExceptionWhenLoadUserByUsernameAndUsernameDoesNotExist() {
        when(userRepository.findByNickname(NICKNAME)).thenReturn(empty());

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(NICKNAME))
                .hasMessage("There is no user with nickname: " + NICKNAME)
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void shouldSetNicknameToUserDetailsWhenLoadUserByUsername() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.getUsername()).isEqualTo(NICKNAME);
    }

    @Test
    void shouldSetPasswordToUserDetailsWhenLoadUserByUsername() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void shouldSetRoleToUserDetailsWhenLoadUserByUsername() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        final GrantedAuthority authority = userDetails.getAuthorities().iterator().next();
        assertThat(authority.getAuthority()).isEqualTo(ROLE);
    }

    @Test
    void shouldSetTrueAccountNonExpiredToUserDetailsWhenLoadUserByUserName() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    void shouldSetTrueAccountNonLockedToUserDetailsWhenLoadUserByUserName() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.isAccountNonLocked()).isTrue();
    }

    @Test
    void shouldSetTrueCredentialsNonExpiredToUserDetailsWhenLoadUserByUserName() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
    }

    @Test
    void shouldSetTrueEnabledToUserDetailsWhenLoadUserByUserName() {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(NICKNAME);

        assertThat(userDetails.isEnabled()).isTrue();
    }
}
