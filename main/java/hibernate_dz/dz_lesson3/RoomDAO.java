package hibernate_dz.dz_lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RoomDAO extends GeneralDAO<Room> {

    private static final String SQL_GET_ROOM_BY_ID = "FROM Room WHERE ID = :idParam";
    //проверить что отель привязанный к комнате уже есть в базе
    //для этого надо сделать запрос в базу данных отелей (в таблицу HOTEL)

    public void delete(long id) throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.delete(findById(id, SQL_GET_ROOM_BY_ID));

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
}
