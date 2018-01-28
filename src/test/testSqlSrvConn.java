package test;

import sql.SqlSrvDBConn;

public class testSqlSrvConn {
    public static void main(String arg[]) {
        SqlSrvDBConn sqlSrvDBConn = new SqlSrvDBConn();
        String result[][] = sqlSrvDBConn.getInformation("select * from teacher");
        int r = result.length;
        int c = result[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }
}
