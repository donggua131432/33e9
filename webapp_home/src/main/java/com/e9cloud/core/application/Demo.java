package com.e9cloud.core.application;
import java.sql.*;

/**
 * Created by Administrator on 2016/4/1.
 */
public class Demo {

        public static final String url = "jdbc:mysql://10.0.7.165:1234/e9cloud_home";
        public static final String name = "com.mysql.jdbc.Driver";
        public static final String user = "root";
        public static final String password = "33E9.com";

        public Connection conn = null;
        public PreparedStatement pst = null;

        public Demo() {
            try {
                Class.forName(name);//指定连接类型
                conn = DriverManager.getConnection(url, user, password);//获取连接
                pst = conn.prepareStatement("select * from tb_user_admin");//准备执行语句
                ResultSet rs = pst.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                this.conn.close();
                this.pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void main(String[] args) {
            new Demo();
        }
}
