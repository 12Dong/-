package Main;

import sql.SqlSrvDBConn;

import java.sql.*;

//学生类 具有基本查询权限 与 选课权限 退课权限
public class student {
    public UserTable userTable = null;

    public void setUserTable(UserTable userTable) {
        this.userTable = userTable;
    }

    public UserTable getUserTable() {
        return userTable;
    }

    //返回 登录学生信息
    public String[][]  getStudentInformation(){
        String sql = "select * from student where xh = \'" + userTable.getId()+'\'';
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation(sql);
        sqlSrvDBConn.closeConn();
        return result;
    }

    //返回 学生选课信息
    public String[][] getStudentCourse(){
        String sql = "select * from elect where xh = \'" + userTable.getId()+"\'";
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
    //学生 选课成功返回 true 选课失败 返回 false
    public boolean selectCourse(String xq,String kh,String gh){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            ResultSet rs = sqlSrvDBConn.executeQuery("select * from opencourse");
            boolean status = false;
            while(rs.next()){
                if(rs.getString("xq").trim().compareTo(xq)==0 &&
                        rs.getString("kh").trim().compareTo(kh)==0 &&
                        rs.getString("gh").trim().compareTo(gh)==0){
                    status = true;
                    break;
                }
            }
            if(status==false) return false;
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            ResultSet rs = sqlSrvDBConn.executeQuery("select * from elect");
            boolean status = false;
            while(rs.next()){
                if(rs.getString("xq").trim().compareTo(xq)==0 &&
                        rs.getString("kh").trim().compareTo(kh)==0 &&
                        rs.getString("gh").trim().compareTo(gh)==0 &&
                        rs.getString("xh").trim().compareTo(userTable.getId())==0){
                    status = false;
                    break;
                }
            }
            if(status==false) return false;
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            pstmt = conn.prepareStatement("insert into elect values (?,?,?,?,null,null,null)");
            pstmt.setString(1,userTable.getId());
            pstmt.setString(2,xq);
            pstmt.setString(3,kh);
            pstmt.setString(4,gh);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        sqlSrvDBConn.closeConn();
        return true;

    }

    //学生退课 退课成功返回 true 退课失败 返回 false
    public boolean deletetCourse(String xq,String kh,String gh){
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        PreparedStatement pstmt = null;
        Connection conn = sqlSrvDBConn.getConn();
        try{
            ResultSet rs = sqlSrvDBConn.executeQuery("select * from elect");
            boolean status = false;
            while(rs.next()){
                if(rs.getString("xq").trim().compareTo(xq)==0 &&
                        rs.getString("kh").trim().compareTo(kh)==0 &&
                        rs.getString("gh").trim().compareTo(gh)==0 &&
                        rs.getString("xh").trim().compareTo(userTable.getId())==0){
                    status = true;
                    break;
                }
            }
            if(status==false) return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            pstmt = conn.prepareStatement("delete from elect where xh = ? and xq = ?  and kh = ?  and gh = ? ");
            pstmt.setString(1,userTable.getId());
            pstmt.setString(2,xq);
            pstmt.setString(3,kh);
            pstmt.setString(4,gh);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;

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
}
