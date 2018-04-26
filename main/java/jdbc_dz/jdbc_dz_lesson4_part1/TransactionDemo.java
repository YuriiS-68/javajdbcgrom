package jdbc_dz.jdbc_dz_lesson4_part1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public void save(List<Product> products){
        try(Connection connection = getConnection()) {

            saveList(products, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void saveList(List<Product> products, Connection connection)throws SQLException{
        long badId = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES(?, ?, ?, ?)")) {

            connection.setAutoCommit(false);

            for (Product product : products){
                badId = product.getId();
                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDescription());
                preparedStatement.setInt(4, product.getPrice());

                int res = preparedStatement.executeUpdate();

                System.out.println("save was finished with result " + res);
            }

            connection.commit();

        }catch (SQLException e){
            connection.rollback();
            System.err.println("Product with id - " + badId + " failed to save in DB.");
            throw e;
        }
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
