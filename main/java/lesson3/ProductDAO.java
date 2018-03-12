package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //CRUD
    //create, read, update, delete

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public Product save(Product product){

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES(?, ?, ?, ?)");

            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());

            int res = preparedStatement.executeUpdate();

            System.out.println("save was finished with result " + res);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> getProducts(){
        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");

            List<Product> products = new ArrayList<>();
            while (resultSet.next()){
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4));
                products.add(product);
            }
            return products;
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;
    }

    public Product update(Product product){

        Product productAfterUpdate = new Product();

        try(Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, PRICE = ? WHERE ID = " + product.getId()+ "");

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());

            int res = preparedStatement.executeUpdate();

            System.out.println("save was finished with result " + res);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE ID = " + product.getId() + "");

            while (resultSet.next()){
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                int price = resultSet.getInt(4);

                productAfterUpdate = new Product(id, name , description, price);

            }
            statement.close();
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return productAfterUpdate;
    }

    public void delete(long id){
        try(Connection connection = getConnection()) {

            Statement statement = connection.createStatement();

            int response = statement.executeUpdate("DELETE FROM PRODUCT WHERE ID = " + id + "");

            System.out.println("delete was finished with result " + response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
