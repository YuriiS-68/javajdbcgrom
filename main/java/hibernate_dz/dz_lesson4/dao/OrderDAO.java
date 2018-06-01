package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;

public class OrderDAO extends GeneralDAO<Order> {

    private UserDAO userDAO = new UserDAO();
    private HotelDAO hotelDAO = new HotelDAO();
    private RoomDAO roomDAO = new RoomDAO();

    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM ORDER_DZ4 WHERE ID = :idParam";

    public void bookRoom(Long roomId, Long userId, Long hotelId)throws Exception{
        //проверить есть ли объекты с такими id в базе данных
        //если есть создать ордер
        //сохранить ордер в базе
        validate(roomId, userId, hotelId);

        save(createOrder(roomId, userId));
    }

    @SuppressWarnings("unchecked")
    public void cancelReservation(Long roomId, Long userId){

        Transaction tr = null;
        try(Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            Query<Order> query = session.createQuery("DELETE FROM Order_ WHERE ID_USER = :idParam AND ID_ROOM = :idParam");
            query.setParameter("idParam", userId).setParameter("idParam", roomId);
            query.executeUpdate();

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();

            throw e;
        }
    }

    public void delete(long id)throws BadRequestException{

        setType(Order.class);

        delete(id, SQL_GET_ORDER_BY_ID);
    }

    private Order createOrder(Long roomId, Long userId)throws Exception{
        if (roomId == 0 || userId == 0)
            throw new BadRequestException("Invalid incoming data");

        Order order = new Order();

        String dateFrom = "10.05.2018";
        String dateTo = "16.05.2018";
        Date dateStart = GeneralDAO.getFORMAT().parse(dateFrom);
        Date dateFinish = GeneralDAO.getFORMAT().parse(dateTo);

        order.setUser(userDAO.findById(userId));

        order.setRoom(roomDAO.findById(roomId));

        order.setDateFrom(GeneralDAO.getFORMAT().parse(dateFrom));
        order.setDateTo(GeneralDAO.getFORMAT().parse(dateTo));

        order.setMoneyPaid(orderCost(dateStart, dateFinish, roomId));

        return order;
    }

    private double orderCost(Date dateStart, Date dateFinish, Long roomId)throws Exception{
        if (dateStart == null || dateFinish == null || roomId == 0)
            throw new BadRequestException("Invalid incoming data");

        long difference = dateStart.getTime() - dateFinish.getTime();
        int days = (int)(difference / (24 * 60 * 60 * 1000));

        return days * roomDAO.findById(roomId).getPrice();
    }

    private void validate(long roomId, long userId, long hotelId)throws BadRequestException{

        if (userDAO.findById(userId).getId() != userId)
            throw new BadRequestException("There is no user with id in the database - " + userId);

        if (hotelDAO.findById(hotelId).getId() != hotelId)
            throw new BadRequestException("There is no hotel with id in the database - " + hotelId);

        if (roomDAO.findById(roomId).getId() != hotelId)
            throw new BadRequestException("There is no room with id in the database - " + roomId);
    }

    private Order findById(long id){

        setType(Order.class);

        return findById(id, SQL_GET_ORDER_BY_ID);
    }
}
