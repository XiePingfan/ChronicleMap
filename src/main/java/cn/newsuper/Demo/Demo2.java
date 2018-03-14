package cn.newsuper.Demo;

import java.sql.*;

/**
 * Created by Administrator on 2018/3/8.
 */
public class Demo2 {
    public static void main(String[] args)  {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://192.168.2.129:3306/app","root","wode");
            String sql = "INSERT INTO problem (HOUR_ID,CITY_NAME,SEN_NAME,AREA_NAME,IMSI,TRAFFIC_ALL_HOURS) VALUES (?,?,?,?,?,?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1,1212);
            prep.setString(2,"ss");
            prep.setString(3,"ss");
            prep.setString(4,"ss");
            prep.setString(5,"ss");
            prep.setLong(6,222);
            prep.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
