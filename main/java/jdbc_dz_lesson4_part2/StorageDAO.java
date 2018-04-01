package jdbc_dz_lesson4_part2;

import java.sql.*;

public class StorageDAO extends GeneralDAO<Storage>{

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public Storage save(Storage storage){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGE_ VALUES(?, ?, ?, ?)")) {

            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, storage.getFormatSupported());
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageSize());

            int res = preparedStatement.executeUpdate();

            System.out.println("save storage with id " + storage.getId() + " was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return storage;
    }

    public void delete(long id){

    }

    public Storage update(Storage storage){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET FORMAT_SUPPORTED = ?, " +
                    "COUNTRY_STORAGE = ?, SIZE_STORAGE = ? WHERE STORAGE_ID = " + storage.getId()+ "")) {

            preparedStatement.setString(1, storage.getFormatSupported());
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageSize());

            int res = preparedStatement.executeUpdate();

            System.out.println("update storage was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return storage;
    }

    public Storage findById(long id){

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STORAGE_ WHERE STORAGE_ID = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Storage storage = new Storage();
            while (resultSet.next()){
                storage = new Storage(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getLong(4));
            }
            return storage;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
