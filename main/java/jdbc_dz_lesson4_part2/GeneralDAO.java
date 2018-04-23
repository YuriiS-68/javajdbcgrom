package jdbc_dz_lesson4_part2;

import java.sql.*;
import java.util.List;

public class GeneralDAO<T> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    void update(Storage storage, File file)throws Exception{
        try(Connection connection = getConnection()) {

            update(storage, file, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    void delete(Storage storage, File file)throws Exception{
        try(Connection connection = getConnection()) {

            delete(storage, file, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    void transfer(Storage storageFrom, Storage storageTo, File file)throws Exception{
        try(Connection connection = getConnection()) {

            transfer(storageFrom, storageTo, file, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    void transferAll(List<File> filesFrom, Storage storageFrom, Storage storageTo)throws Exception{
        try(Connection connection = getConnection()) {

            transferAll(storageFrom, storageTo, filesFrom, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void transferAll(Storage storageFrom, Storage storageTo, List<File> filesFrom, Connection connection)throws Exception{

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);

            for (File file : filesFrom){
                preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");
                updateFileTable(file, preparedStatement, connection);
            }

            preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");

            updateStorageTable(storageFrom, preparedStatement, connection);

            updateStorageTable(storageTo, preparedStatement, connection);

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

    private void transfer(Storage storageFrom, Storage storageTo, File file, Connection connection)throws Exception{

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");

            updateFileTable(file, preparedStatement, connection);

            preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");

            updateStorageTable(storageFrom, preparedStatement, connection);

            updateStorageTable(storageTo, preparedStatement, connection);

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

    private void delete(Storage storage, File file, Connection connection)throws Exception{

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("DELETE FROM FILE_ WHERE FILE_ID = ?");

            deleteFileTable(file, preparedStatement, connection);

            preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");

            updateStorageTable(storage, preparedStatement, connection);

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

    private void update(Storage storage, File file, Connection connection)throws Exception{

        PreparedStatement preparedStatement = null;
        try{
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("UPDATE FILE_ SET STORAGE_ID = ? WHERE FILE_ID = ?");

            updateFileTable(file, preparedStatement, connection);

            preparedStatement = connection.prepareStatement("UPDATE STORAGE_ SET SIZE_STORAGE = ? WHERE STORAGE_ID = ?");

            updateStorageTable(storage, preparedStatement, connection);

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

    private void updateFileTable(File file, PreparedStatement preparedStatement, Connection connection)throws Exception{
        if (file == null || preparedStatement == null || connection == null)
            throw new Exception("Incoming data contains an error");

        try {
            preparedStatement.setLong(1, file.getStorageId());
            preparedStatement.setLong(2, file.getId());

            boolean res = preparedStatement.execute();

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void updateStorageTable(Storage storage, PreparedStatement preparedStatement, Connection connection)throws Exception{
        if (storage == null || preparedStatement == null || connection == null)
            throw new Exception("Incoming data contains an error");

        try {
            preparedStatement.setLong(1, storage.getStorageSize());
            preparedStatement.setLong(2, storage.getId());

            boolean res = preparedStatement.execute();

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void deleteFileTable(File file, PreparedStatement preparedStatement, Connection connection)throws Exception{
        if (file == null || preparedStatement == null || connection == null)
            throw new Exception("Incoming data contains an error");

        try {
            preparedStatement.setLong(1, file.getId());

            boolean res = preparedStatement.execute();

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
