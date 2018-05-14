package hibernate_dz.dz_lesson3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HotelDAO extends GeneralDAO<Hotel> {

    private static final String SQL_GET_HOTEL_BY_ID = "FROM Hotel WHERE ID = :idParam";
    private static final String SQL_UPDATE_ROOM = "UPDATE ROOM SET ID_HOTEL = ? WHERE ID_HOTEL = ?";
    private static final String SQL_DELETE_HOTEL = "DELETE FROM HOTEL WHERE ID = ?";

    public static void delete(long id)throws Exception {
        if (id == 0)
            throw new Exception("Incorrect data entered");
        //если у комнаты есть отель, просетить в таблице комнат, колонке ID_HOTEL значение null
        //удалить отель
        //если комнат нет, удаляю отель
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ROOM);
            connection.setAutoCommit(false);

            preparedStatement.setNull(1, java.sql.Types.NULL);
            preparedStatement.setLong(2, id);

            int res = preparedStatement.executeUpdate();

            System.out.println("update rows was finished with result " + res);

            preparedStatement = connection.prepareStatement(SQL_DELETE_HOTEL);

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
                connection.setAutoCommit(true);
                connection.close();
            }

            if (preparedStatement != null){
                preparedStatement.close();
            }
        }
    }

    private Hotel findById(long id)throws Exception{
        if (id == 0)
            throw new Exception("Incorrect data entered");

        return findById(id, SQL_GET_HOTEL_BY_ID);
    }
}
