package Main;

import sql.SqlSrvDBConn;

import java.sql.*;

//教师类 具有基本查询权限 与 开课权限 评分权限
public class teacher{
    public UserTable userTable = null;

    public void setUserTable(UserTable userTable) {
        this.userTable = userTable;
    }

    public UserTable getUserTable() {
        return userTable;
    }

    //返回 登录老师信息
    public String[][]  getStudentInformation(){
        String sql = "select * from teacher where gh = \'" + userTable.getId()+'\'';
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation(sql);
        sqlSrvDBConn.closeStatement();
        sqlSrvDBConn.closeConn();
        return result;
    }
    //返回 老师开课信息
    public String[][] getStudentCourse(){
        String sql = "select * from opencourse where gh = \'" + userTable.getId()+'\'';
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation(sql);
        sqlSrvDBConn.closeStatement();
        sqlSrvDBConn.closeConn();
        return result;
    }

    // 返回 学期开课信息
    public String[][] getOpenCourse(String xq){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        Connection conn = sqlSrvDBConn.getConn();
        String result[][]= null;
        try{
            PreparedStatement pstmt = conn.prepareStatement("select * from opencourse where xq = ?");
            pstmt.setString(1,xq);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount =  metaData.getColumnCount();
            rs.last();
            int recordAmount = rs.getRow();
            result = new String[recordAmount+1][columnCount+1];
            for(int i=1;i<=columnCount;i++){
                result[0][i-1]=metaData.getColumnName(i);
            }
            int i=1;
            rs.beforeFirst();
            while(rs.next()){
                for(int j=1;j<=columnCount;j++){
                    result[i][j-1]=rs.getString(j);
                }
                i++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        sqlSrvDBConn.closeStatement();
        sqlSrvDBConn.closeConn();
        return result;
    }

    //老师 开课
    public void selectCourse(String xq,String kh,String gh,String sksj){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            pstmt = conn.prepareStatement("insert into opencourse values (?,?,?,?,?)");
            pstmt.setString(1,userTable.getId());
            pstmt.setString(2,xq);
            pstmt.setString(3,kh);
            pstmt.setString(4,gh);
            pstmt.setString(5,sksj);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    // 老师 评分
    public void score(String xh,String xq,String kh,Integer pscj,Integer kscj,Integer zpcj){
            SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
            PreparedStatement pstmt = null;
            Connection conn = sqlSrvDBConn.getConn();
            try{
                pstmt = conn.prepareStatement("update elect set pscj=? ,kscj = ?,zpcj = ?,where xh= ? and xq = ? and kh = ?");
                pstmt.setInt(1,pscj);
                pstmt.setInt(2,kscj);
                pstmt.setInt(3,zpcj);
                pstmt.setString(4,xh);
                pstmt.setString(5,xq);
                pstmt.setString(6,xq);
                pstmt.setString(7,kh);
                pstmt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
    }
}
