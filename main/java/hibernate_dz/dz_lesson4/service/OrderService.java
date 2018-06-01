package hibernate_dz.dz_lesson4.service;

import hibernate_dz.dz_lesson4.dao.GeneralDAO;
import hibernate_dz.dz_lesson4.dao.OrderDAO;
import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;

public class OrderService {

    private GeneralDAO<Order> orderGeneralDAO = new GeneralDAO<>();
    private OrderDAO orderDAO = new OrderDAO();

    public void bookRoom(Long roomId, Long userId, Long hotelId)throws Exception{

        orderDAO.bookRoom(roomId, userId, hotelId);
    }

    public void cancelReservation(Long roomId, Long userId){

        orderDAO.cancelReservation(roomId, userId);
    }

    public void update(Order order)throws BadRequestException{

        orderGeneralDAO.update(order);
    }

    public void delete(long id)throws BadRequestException{

        orderDAO.delete(id);
    }
}
