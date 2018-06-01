package hibernate_dz.dz_lesson4.controller;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.User;
import hibernate_dz.dz_lesson4.service.UserService;

public class UserController {

    private UserService userService = new UserService();

    public User registerUser(User user)throws BadRequestException {

        return userService.registerUser(user);
    }

    public void update(User user)throws BadRequestException{

        userService.update(user);
    }

    public void delete(long id)throws BadRequestException{

        userService.delete(id);
    }
}
