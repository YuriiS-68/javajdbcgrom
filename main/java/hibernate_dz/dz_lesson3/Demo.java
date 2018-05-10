package hibernate_dz.dz_lesson3;

import java.util.Date;

public class Demo {
    public static void main(String[] args)throws Exception{
         GeneralDAO<Hotel> generalDAO = new GeneralDAO<>();
         HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel("NORD", "Ukraine", "Kiev", "Vokzalnaya");
        Hotel hotel2 = new Hotel(180, "Vostok", "China", "Pekin", "Mao");
        Hotel hotel3 = new Hotel("Hilton", "Germany", "Berlin", "Ulrihshtrasse");

        //hotel1.setId(100);
        //hotel2.setId(110);
        //hotel3.setId(55);
        Room room1 = new Room(53, 5, 250.0, 0, 0, new Date(), hotel1);
        Room room2 = new Room(58, 1, 25.50, 0, 0, new Date(), hotel2);
        Room room3 = new Room(54, 2, 40.0, 1, 1, new Date(), hotel1);
        Room room4 = new Room(3, 50.0, 1, 1, new Date(), hotel3);

        //HotelDAO.save(hotel3);
        //HotelDAO.update(hotel1);
        //RoomDAO.save(room2);
        hotelDAO.delete(190);
        //RoomDAO.delete(57);

        //generalDAO.update(hotel1);

    }
}
