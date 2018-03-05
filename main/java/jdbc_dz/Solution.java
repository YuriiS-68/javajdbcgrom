package jdbc_dz;

import java.sql.*;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    private static void getAllProducts(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT")){
                while (resultSet.next()){
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);
                    Product product = new Product(id, name, description, price);
                    System.out.println(product);
                }
            }

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

    }

    private static void getProductsByPrice(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE > 0 AND PRICE <= 100")){
                while (resultSet.next()){
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);
                    Product product = new Product(id, name, description, price);
                    System.out.println(product);
                }
            }

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

    }

    private static void getProductsByDescription(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT")){
                while (resultSet.next()){
                    long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    int price = resultSet.getInt(4);
                    if (description.length() > 50){
                        Product product = new Product(id, name, description, price);
                        System.out.println(product);
                    }
                }
            }

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void saveProduct(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            boolean result = statement.execute("INSERT INTO PRODUCT VALUES(333, 'cup', 'ceramic dishes', 35)");

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void deleteProducts(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME != 'toy'");

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //saveProduct();

        //deleteProducts();

        //getAllProducts();

        //getProductsByPrice();

        getProductsByDescription();
    }
}
