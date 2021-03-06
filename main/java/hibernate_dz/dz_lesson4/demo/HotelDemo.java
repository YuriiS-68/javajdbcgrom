package hibernate_dz.dz_lesson4.demo;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.HotelDAO;
import hibernate_dz.dz_lesson4.model.Hotel;

public class HotelDemo {
    public static void main(String[] args)throws Exception{

        GeneralDAO<Hotel> hotelGeneralDAO = new GeneralDAO<>();
        HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel("HILTON", "England", "London", "street 17");
        Hotel hotel2 = new Hotel("SPUTNIK", "Ukraine", "Kiev", "Vasilkovskaya");
        Hotel hotel3 = new Hotel("SPORT", "Italy", "Rome", "street 23");

        hotelGeneralDAO.save(hotel3);

        //hotelDAO.delete(25);

        //System.out.println(hotelDAO.findHotelByCity("Rome"));

        //System.out.println(hotelDAO.findHotelByCity("London"));
    }
}
