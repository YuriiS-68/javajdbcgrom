package hibernate_dz.dz_lesson4.dao;

import hibernate_dz.dz_lesson4.exception.BadRequestException;
import hibernate_dz.dz_lesson4.model.Order;
import hibernate_dz.dz_lesson4.model.Room;
import hibernate_dz.dz_lesson4.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.Date;
import java.util.List;

public class OrderDAO extends GeneralDAO<Order> {

    private static final String SQL_GET_USER = "FROM User_ WHERE ID = :idParam";
    //private static final String SQL_GET_ALL_HOTEL = "SELECT * FROM HOTEL";
    private static final String SQL_GET_ROOM = "FROM Room WHERE ID = :idParam";

    private GeneralDAO<User> userGeneralDAO = new GeneralDAO<>();
    //private GeneralDAO<Hotel> hotelGeneralDAO = new GeneralDAO<>();
    private GeneralDAO<Room> roomGeneralDAO = new GeneralDAO<>();


    private static final String SQL_GET_ALL_ORDER = "SELECT * FROM ORDER_";

    public void bookRoom(long roomId, long userId, long hotelId)throws Exception{
        //проверить есть ли объекты с такими id в базе данных
        //если есть создать ордер
        //сохранить ордер в базе
        //validate(roomId, userId, hotelId);

        save(createOrder(roomId, userId));
    }

    /*private void validate(long roomId, long userId, long hotelId)throws BadRequestException{

        if (userGeneralDAO.findById(userId, SQL_GET_USER).getId() != userId)
            throw new BadRequestException("There is no user with id in the database - " + userId);

        if (roomGeneralDAO.findById(roomId, SQL_GET_ROOM).getId() != roomId)
            throw new BadRequestException("There is no hotel with id in the database - " + hotelId);

        if (roomGeneralDAO.findById(roomId, SQL_GET_ROOM).getHotel().getId() != hotelId)
            throw new BadRequestException("There is no hotel with id in the database - " + hotelId);
    }*/

    public void cancelReservation(long roomId, long userId){

    }

    public void delete(long id)throws BadRequestException{
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //session.delete(findById(id, SQL_GET_ALL_ORDER));

            System.out.println("Recording deleted successfully");

            tr.commit();
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }finally {
            if (session != null){
                session.close();
            }
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

        //order.setUser(userGeneralDAO.findById(userId, SQL_GET_USER));

        //order.setRoom(roomGeneralDAO.findById(roomId, SQL_GET_ROOM));

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
        double orderCost = 0;
        for (Room room : RoomDAO.getAllRoom()){
            if (room.getId() == roomId){
                orderCost = room.getPrice() * days;
                if (orderCost < 0) {
                    orderCost = -1 * orderCost;
                }
            }
        }
        return orderCost;
    }

    @SuppressWarnings("unchecked")
    private static List<Order> getAllOrder(){

        List<Order> orders;

        try (Session session = createSessionFactory().openSession()){

            NativeQuery query = session.createNativeQuery(SQL_GET_ALL_ORDER);
            orders = query.addEntity(Order.class).list();

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return orders;
    }
}
