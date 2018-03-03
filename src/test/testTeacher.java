package test;

import Main.UserTable;
import Main.teacher;

public class testTeacher  {
        public static void main(String arg[]){
            teacher t = new teacher();
            UserTable userTable = new UserTable();
            userTable.setId("0101");
            userTable.setUsername("陈迪茂");
            t.setUserTable(userTable);
            t.addStudent("1108","王小明","男",new java.sql.Date(new java.util.Date().getTime()),"上海","12345678910","02");
//            String result[][] = t.getTeacherCourse();
//            int r = result.length;
//            int c = result[0].length;
//            for (int i = 0; i < r; i++) {
//                for (int j = 0; j < c; j++) {
//                    System.out.print(result[i][j] + " ");
//                }
//                System.out.println();
//            }
//
//            result = t.getTeacherInformation();
//            r = result.length;
//            c = result[0].length;
//            for (int i = 0; i < r; i++) {
//                for (int j = 0; j < c; j++) {
//                    System.out.print(result[i][j] + " ");
//                }
//                System.out.println();
//            }
//
////            t.openCourse("2012-2013冬季","08305003","随便上");
//            t.score("1102","2013-2014秋季","08305004",null,30,50);

        }

}
