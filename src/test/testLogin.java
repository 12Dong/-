package test;

import Main.UserTable;
import Main.login;

public class testLogin {
    public static void main(String arg[]){
        login trylogin = new login();
        trylogin.setUsername("李明");
        trylogin.setPassword("123456");
        System.out.println(trylogin.tryLogin());
        UserTable userTable = trylogin.getUserTable();
        System.out.println(userTable.getId());

        trylogin.setUsername("陈迪茂");
        trylogin.setPassword("123456");
        System.out.println(trylogin.tryLogin());
        userTable = trylogin.getUserTable();
        System.out.println(userTable.getId());

        trylogin.setUsername("zhangsan");
        trylogin.setPassword("123456");
        System.out.println(trylogin.tryLogin());

    }
}
