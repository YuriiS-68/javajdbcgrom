package hibernate_dz.dz_lesson4.demo;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserDemo {
    public static void main(String[] args) {

        GeneralDAO<User> userGeneralDAO = new GeneralDAO<>();

        Order order1 = new Order();
        Order order2 = new Order();

        List<Order> orders1 = new LinkedList<>();
        orders1.add(order1);
        orders1.add(order2);

        User user1 = new User("User1", "12345", "Ukraine", "USER", orders1);
        User user2 = new User("User2", "12345", "Italy", "USER");
        User user3 = new User("User3", "98765", "Ukraine", "ADMIN");
        User user4 = new User("User4", "12345", "Germany", "USER");
        User user5 = new User("User5", "98765", "Germany", "ADMIN");


        userGeneralDAO.save(user1);
    }
}
