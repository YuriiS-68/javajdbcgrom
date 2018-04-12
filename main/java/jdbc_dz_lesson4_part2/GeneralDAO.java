package jdbc_dz_lesson4_part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneralDAO<T> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public void update(Storage storage, File file){
        try(Connection connection = getConnection()) {

            update(storage, file, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void update(Storage storage, File file, Connection connection)throws SQLException{
        try(PreparedStatement fileStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = " + file.getId() + "");
            PreparedStatement storageStatement = connection.prepareStatement("UPDATE STORAGE_ SET FORMAT_SUPPORTED = ?, " +
                    "COUNTRY_STORAGE = ?, SIZE_STORAGE = ? WHERE STORAGE_ID = " + storage.getId()+ "")) {

            connection.setAutoCommit(false);

            fileStatement.setLong(1, file.getStorageId());

            storageStatement.setString(1, storage.getFormatSupported());
            storageStatement.setString(2, storage.getStorageCountry());
            storageStatement.setLong(3, storage.getStorageSize());

            int resFile = fileStatement.executeUpdate();

            int resStorage = storageStatement.executeUpdate();

            System.out.println("update file with id " + file.getId() + " was finished with result " + resFile);
            System.out.println("update storage with id " + storage.getId() + " was finished with result " + resStorage);

            connection.commit();

        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    public Object save(Object object){

        return object;
    }

    public void delete(long id){

    }

    public Object update(Object object){


        return object;
    }

    public Object findById(long id)throws Exception{


        return null;
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
