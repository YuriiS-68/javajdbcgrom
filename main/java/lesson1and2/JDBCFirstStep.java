package lesson1and2;

import java.sql.*;

public class JDBCFirstStep {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public static void main(String[] args) {

        //System.out.println(jdbcStart());

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            //1. DB Driver
            //2. create connection
            //3. create query/statement
            //4. execute query
            //5. work with result
            //6. close all the connections
            try {
                Class.forName(JDBC_DRIVER);
            }catch (ClassNotFoundException e){
                System.out.println("Class " + JDBC_DRIVER + " not found");
                return;
            }

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM FORUM_CATEGORIES")){
                while (resultSet.next()){
                    System.out.println(resultSet.getString(3));
                }
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    /*public static String jdbcStart(){
        String result = "";
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            try {
                Class.forName(JDBC_DRIVER);
            }catch (ClassNotFoundException e){
                System.out.println("Class " + JDBC_DRIVER + " not found");
            }

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM FORUM_CATEGORIES")){
                int columns = resultSet.getMetaData().getColumnCount();
                while (resultSet.next()){
                    for (int i = 1; i <= columns ; i++) {
                        result = resultSet.getString(i) + "\t";
                    }
                }
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return result;
    }*/
}
