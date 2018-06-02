package hibernate_dz.dz_lesson4.demo;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.OrderDAO;
import hibernate_dz.dz_lesson4.model.Hotel;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.model.Room;
import hibernate_dz.dz_lesson4.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OrderDemo {
    public static void main(String[] args) throws Exception{
        GeneralDAO<Order> generalDAO = new GeneralDAO<>();

        OrderDAO orderDAO = new OrderDAO();

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date = "18.05.2018";
        String date2 = "10.05.2018";

        String dateFrom = "05.05.2018";
        String dateTo = "09.05.2018";

        User user1 = new User("User1", "12345", "Ukraine", "USER");
        User user2 = new User("User2", "12345", "Italy", "USER");

        Hotel hotel1 = new Hotel("HILTON", "England", "London", "street 17");
        Hotel hotel2 = new Hotel("SPUTNIK", "Ukraine", "Kiev", "Vasilkovskaya");

        hotel1.setId(41L);
        Room room1 = new Room(2, 50.0, 1, 0, format.parse(date), hotel1);
        Room room2 = new Room(4, 180.0, 1, 1, format.parse(date2), hotel1);

        user2.setId(23L);
        room1.setId(41L);
        //Order order1 = new Order(user1, room1, format.parse(dateFrom), format.parse(dateTo), 200.00);

        orderDAO.bookRoom(41L, 23L, 41L);
    }
}
