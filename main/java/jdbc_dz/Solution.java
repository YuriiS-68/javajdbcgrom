package jdbc_dz;

import java.sql.*;
import java.util.Arrays;

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

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT DESCRIPTION WHERE LENGTH(DESCRIPTION) > 50")){
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

    private static void increasePrice(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("UPDATE PRODUCT SET PRICE = PRICE + 100 WHERE PRICE < 970");

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static void changeDescription(){
        String sql = "UPDATE PRODUCT SET DESCRIPTION = ? WHERE LENGTH(DESCRIPTION) > 40 AND ID = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement(); PreparedStatement ps = connection.prepareStatement(sql)) {

            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT DESCRIPTION WHERE LENGTH(DESCRIPTION) > 40")){
                while (resultSet.next()){
                    String description = resultSet.getString(3);
                    int idRow = resultSet.getInt(1);

                    String[] arraySentences = description.split("[.]");

                    arraySentences[arraySentences.length - 1] = "";

                    String descriptionNew = "";
                    for (String sentence : arraySentences){
                        if (sentence != null && !sentence.isEmpty()){
                            descriptionNew += sentence + ".";
                        }
                    }

                    ps.setString(1, descriptionNew);
                    ps.setInt(2, idRow);
                    ps.executeUpdate();
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

        //getProductsByDescription();

        //increasePrice();

        changeDescription();

        //changeSentence("railroad with locomotive and wagons. this toy is like every child. she brings joy.");
    }
}
