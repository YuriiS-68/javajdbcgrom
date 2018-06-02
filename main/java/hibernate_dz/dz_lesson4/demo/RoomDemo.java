package hibernate_dz.dz_lesson4.demo;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.RoomDAO;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Hotel;
import hibernate_dz.dz_lesson4.model.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class RoomDemo {
    public static void main(String[] args)throws Exception {
        GeneralDAO<Room> generalDAO = new GeneralDAO<>();

        RoomDAO roomDAO = new RoomDAO();

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date = "18.05.2018";
        String date2 = "12.05.2018";
        String date3 = "13.05.2018";
        String date4 = "14.05.2018";
        String date5 = "23.05.2018";

        Hotel hotel1 = new Hotel("HILTON", "England", "London", "street 17");
        Hotel hotel2 = new Hotel("SPUTNIK", "Ukraine", "Kiev", "Vasilkovskaya");
        Hotel hotel3 = new Hotel("SPORT", "Italy", "Rome", "street 23");

        hotel1.setId(41L);
        hotel2.setId(42L);
        hotel3.setId(43L);
        Room room1 = new Room(2, 50.0, 1, 0, format.parse(date4), hotel1);
        Room room2 = new Room(4, 180.0, 1, 1, format.parse(date2), hotel1);
        Room room3 = new Room(1, 50.0, 0, 1, format.parse(date3), hotel3);
        Room room4 = new Room(3, 175.0, 1, 1, format.parse(date), hotel2);
        Room room5 = new Room(6, 350.0, 1, 1, format.parse(date5), hotel2);

        Filter filter = new Filter(0, 50.0, 1, 0, format.parse(date4), "England", "London");
        Filter filter1 = new Filter(50.0, "England", 1, 0);
        Filter filter2 = new Filter(175.0, 1, 1);

        //System.out.println(roomDAO.findRooms(filter1));

        //room5.setId(26L);
        //generalDAO.save(room1);

        //roomDAO.delete(25);
        //roomDAO.delete(20);

        System.out.println(roomDAO.findRooms(filter1));

        //System.out.println(roomDAO.createQuery(filter2));
    }
}
