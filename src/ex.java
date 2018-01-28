import sql.SqlSrvDBConn;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ex {
    public static void main(String agv[]){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        Connection conn = sqlSrvDBConn.getConn();
        try{
            Statement statement =  conn.createStatement();
            String sql = "update teacher set yxh = '02' where gh = '0101'";
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
