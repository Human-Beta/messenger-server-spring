package com.nikita.messenger.server.controller;

import com.nikita.messenger.server.data.UserData;
import com.nikita.messenger.server.dto.UserDTO;
import com.nikita.messenger.server.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
