package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;
import com.nikita.messenger.server.dto.PaginationDTO;
import com.nikita.messenger.server.dto.UserDTO;
import com.nikita.messenger.server.dto.UserRegistrationDTO;
import com.nikita.messenger.server.facade.UserFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";
    private static final String PASSWORD = "password";
    private static final int PAGE = 42;
    private static final int SIZE = 24;

    @Spy
    @InjectMocks
    private UserController controller;

    @Mock
    private UserFacade userFacade;

    private final UserDTO user = new UserDTO();
    private final UserRegistrationDTO userRegistration = new UserRegistrationDTO();
    private final UserRegistrationData userRegistrationData = new UserRegistrationData();
    private final UserData userData = new UserData();
    private final PaginationDTO pagination = new PaginationDTO(PAGE, SIZE);

    @BeforeEach
    void setUp() {
        userRegistration.setName(NAME);
        userRegistration.setNickname(NICKNAME);
        userRegistration.setPassword(PASSWORD);
    }

    private void mockMap() {
        doReturn(user).when(controller).map(any(UserData.class), eq(UserDTO.class));
    }

    @Test
    void shouldReturnCurrentUserWhenGetCurrentUser() {
        mockMap();
        when(userFacade.getCurrentUser()).thenReturn(userData);

        final UserDTO currentUser = controller.getCurrentUser();

        assertThat(currentUser).isSameAs(user);
    }

    @Test
    void shouldReturnCreatedUserWhenCreateUser() {
        mockMap();
        doReturn(userRegistrationData).when(controller).map(any(UserRegistrationDTO.class), eq(UserRegistrationData.class));
        when(userFacade.createUser(userRegistrationData)).thenReturn(userData);

        final UserDTO currentUser = controller.createUser(userRegistration);

        assertThat(currentUser).isSameAs(user);
    }

    @Test
    void shouldReturnUnknownUsersWhenGetUnknownUsersByNickname() {
        final List<UserData> users = of(userData);
        when(userFacade.getUnknownUsersByNickname(NICKNAME, PAGE, SIZE)).thenReturn(users);
        doReturn(of(user)).when(controller).mapAll(users, UserDTO.class);

        final List<UserDTO> unknownUsers = controller.getUnknownUsersByNickname(NICKNAME, pagination);

        assertThat(unknownUsers).containsExactly(user);
    }
}
