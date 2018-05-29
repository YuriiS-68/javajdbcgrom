package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.User;

public class UserDAO extends GeneralDAO<User> {

    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM USER_DZ4 WHERE ID = :idParam";

    public User registerUser(User user)throws BadRequestException{

        if (user.getId() == null){
            save(user);
        }
        else
            throw new BadRequestException("User with id -  " + user.getId() + " can`t be registered in the database");

        return user;
    }

    public void delete(long id)throws BadRequestException{

        setType(User.class);

        delete(id, SQL_GET_USER_BY_ID);
    }

    public User findById(long id){

        setType(User.class);

        return findById(id, SQL_GET_USER_BY_ID);
    }
}