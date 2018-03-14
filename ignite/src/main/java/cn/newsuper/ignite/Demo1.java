package cn.newsuper.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2018/3/13.
 */
public class Demo1 {

    public static void main(String[] args) throws Exception {
        //注册ignite驱动
      //  Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
        //连接驱动
      //  final Connection conn = DriverManager.getConnection("jdbc:ignite:thin://192.168.25.144");


        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            // Put values in cache.
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");

            cache.put(1, "Hello");
            cache.put(2, "World!");

            // Get values from cache and
            // broadcast 'Hello World' on all the nodes in the cluster.
            ignite.compute().broadcast(() -> {
                String hello = cache.get(1);
                String world = cache.get(2);

                System.out.println(hello + " " + world);
            });
        }
    }


    /**
     * 第一个计算应用
     */
    private static void test01() {
        try (Ignite ignite = Ignition.start("C:\\Users\\Administrator\\Downloads\\apache-ignite-fabric-2.3.0-bin/examples/config/example-ignite.xml")) {
            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
            // Iterate through all the words in the sentence and create Callable jobs.
            for (final String word : "Count characters using callable".split(" "))
                calls.add(word::length);
            // Execute collection of Callables on the grid.
            Collection<Integer> res = ignite.compute().call(calls);
            // Add up all the results.
            int sum = res.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total number of characters is '" + sum + "'.");
        }
    }

    /**
     * 查询数据
     * @param conn
     * @throws SQLException
     */
    private static void query(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs =
                         stmt.executeQuery("SELECT p.name, c.name " +
                                 " FROM Person p, City c " +
                                 " WHERE p.city_id = c.id")) {
                while (rs.next())
                    System.out.println(rs.getString(1) + ", " + rs.getString(2));
            }
        }
    }

    /**
     * 创建表
     * @param conn
     * @throws SQLException
     */
    private static void createTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");
            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");
            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");
            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        }
    }

    //插入数据
    private static void insert(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "Forest Hill");
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Denver");
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "St. Petersburg");
            stmt.executeUpdate();
        }
        // Populate Person table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {
            stmt.setLong(1, 1L);
            stmt.setString(2, "John Doe");
            stmt.setLong(3, 3L);
            stmt.executeUpdate();
            stmt.setLong(1, 2L);
            stmt.setString(2, "Jane Roe");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
            stmt.setLong(1, 3L);
            stmt.setString(2, "Mary Major");
            stmt.setLong(3, 1L);
            stmt.executeUpdate();
            stmt.setLong(1, 4L);
            stmt.setString(2, "Richard Miles");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
        }
    }
}
