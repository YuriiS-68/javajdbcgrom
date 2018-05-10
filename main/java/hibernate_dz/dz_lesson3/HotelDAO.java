package hibernate_dz.dz_lesson3;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_HOTEL_BY_ID = "FROM Hotel WHERE ID = :idParam";

    public static Hotel save(Hotel hotel){

        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            session.save(hotel);

            tr.commit();

            System.out.println("Save is done");
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
        return hotel;
    }

    public void delete(long id)throws Exception {
        if (id == 0)
            throw new Exception("Incorrect data entered");
        //если у комнаты есть отель, просетить в таблице комнат, колонке ID_HOTEL значение null
        //удалить отель
        //если комнат нет, удаляю отель
        Connection connection = getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ROOM SET ID_HOTEL = ? WHERE ID_HOTEL = ?");
            connection.setAutoCommit(false);

            preparedStatement.setNull(1, java.sql.Types.NULL);
            preparedStatement.setLong(2, id);

            int res = preparedStatement.executeUpdate();

            System.out.println("update rows was finished with result " + res);

            preparedStatement = connection.prepareStatement("DELETE FROM HOTEL WHERE ID = ?");

            preparedStatement.setLong(1, id);

            int response = preparedStatement.executeUpdate();

            System.out.println("delete was finished with result " + response);

            connection.commit();
            System.out.println("Recording deleted successfully");
        }catch (SQLException e){
            connection.rollback();
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }

    public static Hotel findById(long id)throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        Hotel hotel;

        try( Session session = createSessionFactory().openSession()) {

            Query query = session.createQuery(SQL_GET_HOTEL_BY_ID);
            query.setParameter("idParam", id);
            if (query.uniqueResult() != null){
                hotel = (Hotel) query.uniqueResult();
            }else
                throw new Exception("There is no hotel " + id + " in the database");

        }catch (HibernateException e){
            System.err.println("Save is failed");
            System.err.println(e.getMessage());
            throw e;
        }
        return hotel;
    }
}