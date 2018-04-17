package jdbc_dz_lesson4_part2;

import java.sql.*;

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

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");

            preparedStatement.setLong(1, file.getStorageId());
            preparedStatement.setLong(2, file.getId());

            boolean resFile = preparedStatement.execute();
            System.out.println("Update table File " + resFile);

            preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");

            preparedStatement.setLong(1, storage.getStorageSize());
            preparedStatement.setLong(2, storage.getId());

            boolean resStorage = preparedStatement.execute();
            System.out.println("Update table Storage " + resStorage);

            connection.commit();

        }catch (SQLException e){
            connection.rollback();
            throw e;
        }finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }

            connection.setAutoCommit(true);
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
