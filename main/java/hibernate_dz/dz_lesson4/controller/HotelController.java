package hibernate_dz.dz_lesson4.controller;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Hotel;
import hibernate_dz.dz_lesson4.service.HotelService;

import java.util.List;

public class HotelController {

    private HotelService hotelService = new HotelService();

    public Hotel save(Hotel hotel)throws BadRequestException {

        return hotelService.save(hotel);
    }

    public List<Hotel> findHotelByName(String name){

        return hotelService.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city){

        return hotelService.findHotelByCity(city);
    }

    public void update(Hotel hotel)throws BadRequestException{

        hotelService.update(hotel);
    }

    public void delete(long id)throws BadRequestException{

        hotelService.delete(id);
    }
}
