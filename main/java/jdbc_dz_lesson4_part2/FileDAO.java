package jdbc_dz_lesson4_part2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileDAO extends GeneralDAO<File> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public File save(File file){

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FILE_ VALUES(?, ?, ?, ?, ?)")) {

            preparedStatement.setLong(1, file.getId());
            if (file.getStorageId() == 0){
                preparedStatement.setNull(2, Types.NULL);
            }else {
                preparedStatement.setLong(2, file.getStorageId());
            }
            preparedStatement.setString(3, file.getName());
            preparedStatement.setString(4, file.getFormat());
            preparedStatement.setLong(5, file.getSize());

            int res = preparedStatement.executeUpdate();

            System.out.println("save file with id " + file.getId() + " was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return file;
    }

    public void delete(long id){
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FILE_ WHERE FILE_ID = ?")) {

            preparedStatement.setLong(1, id);

            int response = preparedStatement.executeUpdate();

            System.out.println("delete was finished with result " + response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void update(File file){
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = " + file.getId() + "")) {

            preparedStatement.setLong(1, file.getStorageId());

            int res = preparedStatement.executeUpdate();

            System.out.println("update file with id " + file.getId() + " was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public File findById(long id)throws Exception{

        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILE_ WHERE FILE_ID = ?")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            File file = new File();
            while (resultSet.next()){
                file = new File(resultSet.getLong(1), resultSet.getLong(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getLong(5));
            }
            return file;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    public List<File> findById(Storage storage)throws Exception{

        List<File> files = new ArrayList<>();

        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILE_ WHERE STORAGE_ID = ?")) {

            preparedStatement.setLong(1, storage.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            File file = new File();
            while (resultSet.next()){
                file = new File(resultSet.getLong(1), resultSet.getLong(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getLong(5));
                files.add(file);
            }
            return files;
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
