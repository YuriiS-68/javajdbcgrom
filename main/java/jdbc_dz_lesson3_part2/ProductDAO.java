package jdbc_dz_lesson3_part2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public List<Product> findProductsByPrice(int price, int delta){

        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE >= " + (price - delta) + " AND PRICE <= " + (price + delta) + "");

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

    public List<Product> findProductsByName(String word)throws Exception{
        //слово не корректно если:
        //в входящей строке больше одного слова
        //длина слова меньше 3
        //слово содержит спецсимволы
        //бросать ошибку и в описании должно быть само слово и описание ошибки
        if (!validateWord(word))
            throw new Exception("The word did not pass the validation");

        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE NAME = '" + word + "'");

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

    public List<Product> findProductsWithEmptyDescription(){
        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE DESCRIPTION IS NULL OR DESCRIPTION = ' ' OR LENGTH(DESCRIPTION)=0");

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

    private boolean validateWord(String word)throws Exception{
        if (word == null)
            throw new NullPointerException("Incoming data not exist");

        String[] str;

        str = word.split(" ");

        if (str.length != 1)
            throw new Exception("Incoming string - " + word + " contains more than one word");

        if (word.length() < 3)
            throw new Exception("The " + word + " contains less than three characters");

        if (!checkWord(word))
            throw new Exception("Invalid input entered " + word);

        return true;
    }

    private boolean checkWord(String word)throws NullPointerException{
        if (word == null)
            throw new NullPointerException("Incoming data not exist");

        char[] chars = word.toCharArray();
        for (char ch : chars){
            if(!Character.isLetterOrDigit(ch)){
                return false;
            }
        }
        return true;
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
