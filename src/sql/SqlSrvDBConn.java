package sql;

import java.sql.*;
//基础sql类
public class SqlSrvDBConn {
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    //初始化建立连接
    public SqlSrvDBConn(){
        statement = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            String url = "jdbc:mysql://localhost:3306/databasehw?userSSL=true";
            String user = "host";
            String password = "HanDong85";
            conn = DriverManager.getConnection(url,user,password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //返回Connection
    public Connection getConn(){
        return this.conn;
    }
    //执行查询并返回
    public ResultSet executeQuery(String sql){
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    //关闭Statement
    public void closeStatement(){
        try{
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    //关闭Connection
    public void closeConn(){
        try{
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String[][]  getInformation(String sql){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        ResultSet rs;
        String table[][] = null;
        try{
            rs = sqlSrvDBConn.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount =  metaData.getColumnCount();
            rs.last();
            int recordAmount = rs.getRow();
            table = new String[recordAmount+1][columnCount+1];
            for(int i=1;i<=columnCount;i++){
                table[0][i-1]=metaData.getColumnName(i);
            }
            int i=1;
            rs.beforeFirst();
            while(rs.next()){
                for(int j=1;j<=columnCount;j++){
                    table[i][j-1]=rs.getString(j);
                }
                i++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return table;
    }
}
