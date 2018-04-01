package jdbc_dz_lesson4_part2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GeneralDAO<T> {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lesson.cjqbbseqr63c.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ifgjrkzr";

    private static String pathDB;

    public Object save(Object object){

        return object;
    }

    public void delete(long id){

    }

    public Object update(Object object){


        return object;
    }

    public Object findById(long id)throws Exception{


        return null;
    }

    private Connection getConnection()throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
