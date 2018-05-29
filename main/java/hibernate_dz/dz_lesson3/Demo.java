package hibernate_dz.dz_lesson3;

import java.util.Date;

public class Demo {
    public static void main(String[] args){
         GeneralDAO<Hotel> hotelGeneralDAO = new GeneralDAO<>();
         GeneralDAO<Room> roomGeneralDAO = new GeneralDAO<>();
         RoomDAO roomDAO = new RoomDAO();
         HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel("NORD", "Ukraine", "Kiev", "Vokzalnaya");
        Hotel hotel2 = new Hotel(180, "Vostok777", "China", "Pekin", "Mao");
        Hotel hotel3 = new Hotel("Hilton", "Germany", "Berlin", "Ulrihshtrasse");

        //hotel1.setId(100);
        //hotel2.setId(110);
        hotel3.setId(300);
        Room room1 = new Room(5, 250.0, 0, 0, new Date(), hotel1);
        Room room2 = new Room(58, 1, 175.50, 1, 1, new Date(), hotel2);
        Room room3 = new Room(2, 40.0, 1, 1, new Date(), hotel1);
        Room room4 = new Room(3, 50.0, 1, 1, new Date(), hotel3);

        //HotelDAO.save(hotel1);
        //HotelDAO.update(hotel1);
        //roomDAO.save(room3);
        //HotelDAO.delete(200);
        //roomDAO.delete(59);

        //generalDAO.update(hotel2);
        //roomGeneralDAO.update(room2);

        //roomGeneralDAO.save(room4);
        //generalDAO.save(hotel3);

        //System.out.println(room1);

        /*try {
            hotelDAO.findById(29);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
