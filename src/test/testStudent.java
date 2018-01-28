package test;

import Main.UserTable;
import Main.student;
import sql.SqlSrvDBConn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class testStudent {
    public static void main(String arg[]) {
        student s = new student();
        UserTable userTable = new UserTable();
        userTable.setId("1101");
        userTable.setUsername("李明");
        s.setUserTable(userTable);
        String result[][] = s.getStudentInformation();
        int r = result.length;
        int c = result[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        result = s.getStudentCourse();
        r = result.length;
        c = result[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }

        result = s.getOpenCourse("2012-2013冬季");
        r = result.length;
        c = result[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println( s.selectCourse("2012-2013冬季","08305002","0101"));
        System.out.println( s.deletetCourse("2012-2013冬季","08305002","0101"));
    }
}
