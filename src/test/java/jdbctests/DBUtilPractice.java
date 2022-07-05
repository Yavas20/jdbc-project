package jdbctests;

import org.junit.jupiter.api.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

public class DBUtilPractice {

    @Test
    public void test1() {

        DBUtils.createConnection("jdbc:oracle:thin:@52.90.200.128:1521:XE", "hr", "hr");
        String query = "select first_name, last_name, salary, job_id from employees where rowNum < 6";

        List<Map<String, Object>> queryData = DBUtils.getQueryResultMap(query);


        for (Map<String, Object> eachRow : queryData) {

            System.out.println(eachRow);
        }

        DBUtils.destroy();

    }

    @Test
    public void test2(){

        DBUtils.createConnection("jdbc:oracle:thin:@52.90.200.128:1521:XE", "hr", "hr");
        String query = "select first_name, last_name, salary, job_id from employees where rowNum < 2";

        Map<String, Object> rowMap = DBUtils.getRowMap(query);


        System.out.println(rowMap);

        DBUtils.destroy();

    }





}