package jdbc_dz.jdbc_dz_lesson4_part2;

import java.sql.*;

public class FileDAO extends GeneralDAO<File> {

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
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?")) {

            if (file.getStorageId() == 0){
                preparedStatement.setNull(1, Types.NULL);
            }
            else preparedStatement.setLong(1, file.getStorageId());

            preparedStatement.setLong(2, file.getId());

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
}
