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
    public String[][]  getTeacherInformation(){
        String sql = "select * from teacher where gh = \'" + userTable.getId()+'\'';
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation(sql);
        sqlSrvDBConn.closeConn();
        return result;
    }
    //返回 老师开课信息
    public String[][] getTeacherCourse(){
        String sql = "select * from opencourse where gh = \'" + userTable.getId()+'\'';
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation(sql);
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
        sqlSrvDBConn.closeConn();
        return result;
    }

    //老师 开课
    public void openCourse(String xq,String kh,String sksj){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            pstmt = conn.prepareStatement("insert into opencourse values (?,?,?,?)");
            pstmt.setString(1,xq);
            pstmt.setString(2,kh);
            pstmt.setString(3,userTable.getId());
            pstmt.setString(4,sksj);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        sqlSrvDBConn.closeConn();
    }
    public boolean addStudent(String xh,String xm,String xb,Date csrq,String jg,String sjhm,String yxh){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            pstmt = conn.prepareStatement("insert into student values(?,?,?,?,?,?,?)");
            pstmt.setString(1,xh);
            pstmt.setString(2,xm);
            pstmt.setString(3,xb);
            pstmt.setDate(4,csrq);
            pstmt.setString(5,jg);
            pstmt.setString(6,sjhm);
            pstmt.setString(7,yxh);
            pstmt.execute();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        finally{

        }
        return true;
    }

    public boolean deleteStudent(String xh){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            pstmt = conn.prepareStatement("delete from student where xh = ?");
            pstmt.setString(1,xh);
            pstmt.execute();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    // 老师 评分
    public void score(String xh,String xq,String kh,Integer pscj,Integer kscj,Integer zpcj){
            SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
            PreparedStatement pstmt = null;
            Connection conn = sqlSrvDBConn.getConn();
            try{
                pstmt = conn.prepareStatement("update elect set pscj=? ,kscj = ?,zpcj = ? where xh= ? and xq = ? and kh = ? and gh = ?");
                if(pscj!=null) pstmt.setInt(1,pscj);
                else pstmt.setNull(1,Types.INTEGER);
                if(kscj!=null) pstmt.setInt(2,kscj);
                else pstmt.setNull(2,Types.INTEGER);
                if(zpcj!=null) pstmt.setInt(3,zpcj);
                else pstmt.setNull(3,Types.INTEGER);
                pstmt.setString(4,xh);
                pstmt.setString(5,xq);
                pstmt.setString(6,kh);
                pstmt.setString(7,userTable.getId());
                pstmt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
            sqlSrvDBConn.closeConn();
    }

    public String[][] queryAvgScore(){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        String result[][]= null;
        try{
            pstmt = conn.prepareStatement("select kh,avg(zpcj) from elect group by kh" );
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
            conn.close();
        }catch(SQLException e){
            try{
                conn.rollback();
            }catch(SQLException ee)
            {
                ee.printStackTrace();
            }
            e.printStackTrace();
        }

        return result;
    }

    public String[][] queryTermAvgScore(String xq){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        String result[][]= null;
        try{
            pstmt = conn.prepareStatement("select kh,avg(zpcj) from elect where xq = ? group by kh" );
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
            conn.close();
        }catch(SQLException e){
            try{
                conn.rollback();
            }catch(SQLException ee)
            {
                ee.printStackTrace();
            }
            e.printStackTrace();
        }

        return result;
    }
}
