package hibernate_dz.dz_lesson4.service;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.HotelDAO;
import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;

import java.util.List;

public class HotelService {

    private HotelDAO hotelDAO = new HotelDAO();
    private GeneralDAO<Hotel> hotelGeneralDAO = new GeneralDAO<>();

    public Hotel save(Hotel hotel)throws BadRequestException {

        return hotelGeneralDAO.save(hotel);
    }

    public List<Hotel> findHotelByName(String name){

        return hotelDAO.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city){

        return hotelDAO.findHotelByCity(city);
    }

    public void update(Hotel hotel)throws BadRequestException{

        hotelGeneralDAO.update(hotel);
    }

    public void delete(long id)throws BadRequestException{

        hotelDAO.delete(id);
    }
}
