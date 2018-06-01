package hibernate_dz.dz_lesson4.controller;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.service.OrderService;

public class OrderController {

    private OrderService orderService = new OrderService();

    public void bookRoom(Long roomId, Long userId, Long hotelId)throws Exception{

        orderService.bookRoom(roomId, userId, hotelId);
    }

    public void cancelReservation(Long roomId, Long userId){

        orderService.cancelReservation(roomId, userId);
    }

    public void update(Order order)throws BadRequestException {

        orderService.update(order);
    }

    public void delete(long id)throws BadRequestException{

        orderService.delete(id);
    }
}
