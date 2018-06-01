package hibernate_dz.dz_lesson4.service;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.UserDAO;
import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();
    private GeneralDAO<User> userGeneralDAO = new GeneralDAO<>();

    public User registerUser(User user)throws BadRequestException {

        return userDAO.registerUser(user);
    }

    public void update(User user)throws BadRequestException{

        userGeneralDAO.update(user);
    }

    public void delete(long id)throws BadRequestException{

        userDAO.delete(id);
    }
}
