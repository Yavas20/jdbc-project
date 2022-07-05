package jdbctests;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

public class ListOfMapExample {

    String dbUrl = "jdbc:oracle:thin:@52.90.200.128:1521:XE";
    String dbUsername = "hr";
    String dbPassword = "hr";


    @Test
    public void test1(){

        List<Map<String, Object>> queryData = new ArrayList<>();

        Map<String , Object> row1 = new HashMap<>();
        row1.put("first_name", "Ali");
        row1.put("last_name", "Den");
        row1.put("salary", 100000);
        row1.put("job_id", "Tester");

        System.out.println(row1);

        Map<String , Object> row2 = new HashMap<>();
        row2.put("first_name", "Ayse");
        row2.put("last_name", "Xen");
        row2.put("salary", 200000);
        row2.put("job_id", "SDET");

        queryData.addAll(Arrays.asList(row1, row2));

        System.out.println("queryData.get(0).get(\"salary\") = " + queryData.get(0).get("salary"));
        System.out.println("queryData.get(1).get(\"job_id\") = " + queryData.get(1).get("job_id"));


    }


    @Test
    public void test2() throws SQLException {

        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select first_name, last_name, salary, job_id " +
                "from employees where rowNum < 5");

        ResultSetMetaData rsmd = resultSet.getMetaData();;
        resultSet.next();



        List<Map<String, Object>> queryData = new ArrayList<>();

        Map<String , Object> row1 = new HashMap<>();
        row1.put(rsmd.getColumnName(1),resultSet.getString(1));
        row1.put(rsmd.getColumnName(2),resultSet.getString(2));
        row1.put(rsmd.getColumnName(3),resultSet.getString(3));
        row1.put(rsmd.getColumnName(4),resultSet.getString(4));


        System.out.println(row1);

        resultSet.next();

        Map<String , Object> row2 = new HashMap<>();
        row2.put(rsmd.getColumnName(1),resultSet.getString(1));
        row2.put(rsmd.getColumnName(2),resultSet.getString(2));
        row2.put(rsmd.getColumnName(3),resultSet.getString(3));
        row2.put(rsmd.getColumnName(4),resultSet.getString(4));

        System.out.println(row2);

        queryData.addAll(Arrays.asList(row1, row2));

        System.out.println(queryData);



        resultSet.close();
        statement.close();
        connection.close();


    }


}
