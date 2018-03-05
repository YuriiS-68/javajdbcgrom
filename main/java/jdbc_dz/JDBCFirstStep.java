package jdbc_dz;

import java.sql.*;

public class JDBCFirstStep {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    private static ResultSet jdbcStart()throws SQLException{

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement()) {
            try {
                Class.forName(JDBC_DRIVER);
            }catch (ClassNotFoundException e){
                System.out.println("Class " + JDBC_DRIVER + " not found");
            }

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM FORUM_CATEGORIES")){
                return resultSet;
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        throw new SQLException("Something went wrong");
    }

    public static void main(String[] args)throws SQLException {

        System.out.println(jdbcStart());

    }
}

