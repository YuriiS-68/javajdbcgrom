package hibernate_dz.dz_lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String SQL_GET_ROOM_BY_ID = "FROM Room WHERE ID = :idParam";

    public static Room save(Room room){

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            if (HotelDAO.findById(room.getHotel().getId()).getId() == room.getHotel().getId()){
                session.save(room);
            }

            tr.commit();

            System.out.println("Save is done");
        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (session != null){
                session.close();
            }
        }
        return room;
    }

    public static void delete(long id) throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
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
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    public static Room findById(long id)throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        Room room;

        try( Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery(SQL_GET_ROOM_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                room = (Room) query.uniqueResult();
            }else
                throw new Exception("There is no hotel " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return room;
    }
}
