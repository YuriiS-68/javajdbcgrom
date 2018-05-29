package hibernate_dz.dz_lesson4.demo;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.UserDAO;
import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserDemo {
    public static void main(String[] args)throws BadRequestException{

        GeneralDAO<User> userGeneralDAO = new GeneralDAO<>();

        UserDAO userDAO = new UserDAO();

        Order order1 = new Order(50.0);
        Order order2 = new Order(30.0);
        Order order3 = new Order(35.0);

        List<Order> orders1 = new LinkedList<>();
        orders1.add(order1);
        orders1.add(order2);

        List<Order> orders2 = new LinkedList<>();
        orders2.add(order3);

        User user1 = new User("User1", "12345", "Ukraine", "USER", orders1);
        User user2 = new User("User2", "12345", "Italy", "USER", orders2);
        User user3 = new User("User3", "98765", "Ukraine", "ADMIN");
        User user4 = new User("User4", "12345", "Germany", "USER");
        User user5 = new User("User5", "98765", "Germany", "ADMIN");
        User user6 = new User("User6", "12345", "Germany", "USER");
        User user7 = new User("User7", "password", "Germany", "USER");

        //user7.setId(233);
        //generalDAO.save(user6);

        //System.out.println(UserDAO.getAllUser());

        //user2.setId(2);

        //userDAO.delete(2);
        //userDAO.registerUser(user7);

        //user5.setId(22);
        //generalDAO.update(user5);

        userDAO.delete(32L);

        //user6.setId(32L);
        //user6.setCountry("USA");
        //user6.setUserType("ADMIN");
        //userGeneralDAO.update(user6);



    }
}
