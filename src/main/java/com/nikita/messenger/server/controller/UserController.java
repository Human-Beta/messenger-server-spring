package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.data.UserRegistrationData;
import com.nikita.messenger.server.dto.UserDTO;
import com.nikita.messenger.server.dto.UserRegistrationDTO;
import com.nikita.messenger.server.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {
    @Autowired
    private UserFacade userFacade;

    @GetMapping("/current")
    public UserDTO getCurrentUser() {
        final UserData currentUser = userFacade.getCurrentUser();

        return map(currentUser, UserDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody final UserRegistrationDTO userRegistrationDTO) {
        final UserRegistrationData userRegistration = map(userRegistrationDTO, UserRegistrationData.class);

        final UserData createdUser = userFacade.createUser(userRegistration);

        return map(createdUser, UserDTO.class);
    }

}
