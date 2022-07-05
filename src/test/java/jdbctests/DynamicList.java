package jdbctests;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

public class DynamicList {

    String dbUrl = "jdbc:oracle:thin:@52.90.200.128:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void test1() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select first_name, last_name, salary, job_id " +
                "from employees where rowNum < 6");

        ResultSetMetaData rsmd = resultSet.getMetaData();;

        List<Map<String, Object>> queryData = new ArrayList<>();

        int colCount = rsmd.getColumnCount();

        while (resultSet.next()){

            Map<String , Object> row = new HashMap<>();

            for (int i = 1; i <= colCount; i++) {

                row.put(rsmd.getColumnName(i), resultSet.getObject(i));

            }

            queryData.add(row);

        }

        System.out.println(queryData);

        for (Map<String, Object> eachRow : queryData) {

            System.out.println(eachRow);

        }






        resultSet.close();
        statement.close();
        connection.close();


    }



}
