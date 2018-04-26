package jdbc_dz.jdbc_dz_lesson3_part3;

import java.sql.*;

public class Solution {
    //Все методы должны замерять скорость операции в миллисекундах и возвращать полученное значение

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    public long testSavePerformance(){
        //добавлять 1000 записей в таблицу с произвольными значениями
        long startTime = System.currentTimeMillis();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES(?, ?, ?)")){

            int count = 0;
            for (int i = 1; i < 1001; i++) {
                TestSpeed testSpeed = new TestSpeed();
                testSpeed.setId(i);
                testSpeed.setSomeString("new string " + i);
                testSpeed.setSomeNumber(i * 2);

                preparedStatement.setLong(1, testSpeed.getId());
                preparedStatement.setString(2, testSpeed.getSomeString());
                preparedStatement.setInt(3, testSpeed.getSomeNumber());

                int res = preparedStatement.executeUpdate();

                count += res;
            }
            System.out.println("Successfully added - " + count + " posts");
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        return endTime - startTime;     //Lead time - 36538
    }

    public long testDeleteByIdPerformance(){
        //удалять 1000 добавленных записей отдельными запросами по полю ID
        long startTime = System.currentTimeMillis();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?")) {

            int count = 0;
            for (int i = 1; i < 1001; i++) {
                preparedStatement.setInt(1, i);
                int response = preparedStatement.executeUpdate();
                count += response;
            }

            System.out.println("Delete was finished with result " + count);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;     //Lead time - 37209
    }

    public long testDeletePerformance(){
        //удалять 1000 записей одним запросом по полю ID
        long startTime = System.currentTimeMillis();

        try(Connection connection = getConnection();
            Statement statement = connection.createStatement()) {

            int response = statement.executeUpdate("DELETE FROM TEST_SPEED WHERE ID >= 1 AND ID <= 1000");

            System.out.println("Delete was finished with result " + response);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;      //Lead time - 1873
    }

    public long testSelectByIdPerformance(){
        //выбирать по очереди 1000 добавленных записей отдельными запросами по полю ID
        long startTime = System.currentTimeMillis();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?")) {

            for (int i = 1; i < 1001; i++) {
                preparedStatement.setInt(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    TestSpeed testSpeed = new TestSpeed(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getInt(3));
                }
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;     //Lead time - 36613
    }

    public long testSelectPerformance(){
        //выбирать 1000 записей одним запросом
        long startTime = System.currentTimeMillis();

        try(Connection connection = getConnection(); Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM TEST_SPEED WHERE ID >= 1 AND ID <= 1000");

            while (resultSet.next()){
                TestSpeed testSpeed = new TestSpeed(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getInt(3));
            }
        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return endTime - startTime;       //Lead time - 5181
    }

    private Connection getConnection()throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
