package jdbc.lesson4;

import jdbc.lesson3.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDemo {
    //1. save order - pay money - receive money + ...
    //2. save order - pay money - receive money
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public static void main(String[] args){
        Product product1 = new Product(55, "!!!", "!!!", 777);
        Product product2 = new Product(66, "!!!", "!!!", 777);
        Product product3 = new Product(66, "!!!", "!!!", 777);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        save(products);
    }

    public static void save(List<Product> products){
        try(Connection connection = getConnection()) {

            saveList(products, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void saveList(List<Product> products, Connection connection)throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES(?, ?, ?, ?)")) {

            connection.setAutoCommit(false);

            for (Product product : products){
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
            throw e;
        }
    }

    private static Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
