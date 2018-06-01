package hibernate_dz.dz_lesson4.controller;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Filter;
import hibernate_dz.dz_lesson4.model.Room;
import hibernate_dz.dz_lesson4.service.RoomService;

import java.util.Collection;

public class RoomController {

    private RoomService roomService = new RoomService();

    public Room save(Room room)throws BadRequestException {

        return roomService.save(room);
    }

    public Collection<Room> findRooms(Filter filter){

        return roomService.findRooms(filter);
    }

    public void update(Room room)throws BadRequestException{

        roomService.update(room);
    }

    public void delete(long id)throws BadRequestException{

        roomService.delete(id);
    }
}
