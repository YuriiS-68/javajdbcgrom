package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.model.User;

public class UserDAO extends GeneralDAO<User> {

    public User registerUser(User user)throws NullPointerException{
        if (user == null)
            throw new NullPointerException("Incorrect data entered");




        return user;
    }
}
