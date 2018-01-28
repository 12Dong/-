package Main;

import sql.SqlSrvDBConn;

import java.sql.*;

//登录类 用来返回登录时的信息
public class login {
    private String username;
    private String password;
    private int status;
    public login(){

    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    //尝试登录 如果说 返回 1为 学生类 创建 学生类
    // 返回 2 为 教师类 创建 教师类
    // 返回 0 则为 登录失败 用户库中没有 相对应的数据
    public int tryLogin(){
        String sql = "select * from user";
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        ResultSet rs = sqlSrvDBConn.executeQuery(sql);
        try{
            while(rs.next()){
                if(rs.getString("username").trim().compareTo(username)==0 && rs.getString("password").trim().compareTo(password)==0){
                    return rs.getInt("status");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
