package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.model.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.Date;

public class OrderDAO extends GeneralDAO<Order> {

    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM ORDER_";

    public void bookRoom(long roomId, long userId, long hotelId)throws Exception{
        //проверить есть ли объекты с такими id в базе данных
        //если есть создать ордер
        //сохранить ордер в базе
        validate(roomId, userId, hotelId);

        save(createOrder(roomId, userId));
    }

    @SuppressWarnings("unchecked")
    public void cancelReservation(long roomId, long userId){

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

        Transaction tr = null;
        try (Session session = createSessionFactory().openSession()){
            tr = session.getTransaction();
            tr.begin();

            session.delete(findById(id));

            System.out.println("Recording deleted successfully");
            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }
    }

    private Order createOrder(long roomId, long userId)throws Exception{
        if (roomId == 0 || userId == 0)
            throw new BadRequestException("Invalid incoming data");

        Order order = new Order();

        String dateFrom = "10.05.2018";
        String dateTo = "16.05.2018";
        Date dateStart = GeneralDAO.getFORMAT().parse(dateFrom);
        Date dateFinish = GeneralDAO.getFORMAT().parse(dateTo);

        order.setUser(UserDAO.findById(userId));

        order.setRoom(RoomDAO.findById(roomId));

        order.setDateFrom(GeneralDAO.getFORMAT().parse(dateFrom));
        order.setDateTo(GeneralDAO.getFORMAT().parse(dateTo));

        order.setMoneyPaid(orderCost(dateStart, dateFinish, roomId));

        return order;
    }

    private static double orderCost(Date dateStart, Date dateFinish, long roomId)throws Exception{
        if (dateStart == null || dateFinish == null || roomId == 0)
            throw new BadRequestException("Invalid incoming data");

        long difference = dateStart.getTime() - dateFinish.getTime();
        int days = (int)(difference / (24 * 60 * 60 * 1000));

        return days * RoomDAO.findById(roomId).getPrice();
    }

    private void validate(long roomId, long userId, long hotelId)throws BadRequestException{

        if (UserDAO.findById(userId).getId() != userId)
            throw new BadRequestException("There is no user with id in the database - " + userId);

        if (RoomDAO.findById(roomId).getId() != roomId)
            throw new BadRequestException("There is no hotel with id in the database - " + hotelId);

        if (HotelDAO.findById(roomId).getId() != hotelId)
            throw new BadRequestException("There is no hotel with id in the database - " + hotelId);
    }

    @SuppressWarnings("unchecked")
    private static Order findById(long id)throws BadRequestException {
        Order order;
        try (Session session = createSessionFactory().openSession()) {

            NativeQuery<Order> query = session.createNativeQuery(SQL_GET_ORDER_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null) {
                order = query.addEntity(Room.class).uniqueResult();
            } else
                throw new BadRequestException("There is no object with id - " + id + " in the database");

        } catch (HibernateException e) {
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return order;
    }
}
