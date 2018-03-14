package cn.newsuper.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.WALMode;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2018/3/13.
 * 获取ignite集群
 */
public class Demo2 {
    public static void main( String[] args ) throws Exception {
            Ignite ignite = getIgnite();
            ignite.active(true);
            final long l = System.currentTimeMillis();
            //testGetPut(ignite);
            final long end = System.currentTimeMillis();
            System.out.println("插入数据时间："+(l-end));
        // testAtomOperation(ignite);;
        while (true){
            getValue(ignite);
            Thread.sleep(5000);
        }
       // ignite.close();
    }

    private static void getValue(Ignite ignite){
        IgniteCache<String, String> cache = ignite.getOrCreateCache("myCache");

        String value = cache.get("mykey_"+1);
        System.out.println(value);
    }

    private static Ignite getIgnite() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("192.168.25.146:47500..47509","192.168.25.144:47500..47509","192.168.25.149:47500..47509"));
        spi.setIpFinder(ipFinder);
       // CacheConfiguration cacheConfiguration = new CacheConfiguration<String,String>();
        //cacheConfiguration.setCacheMode(CacheMode.LOCAL);
      //  cacheConfiguration.setBackups(1);
        IgniteConfiguration cfg = new IgniteConfiguration();

        /*
        //持久化到本地磁盘
        DataStorageConfiguration storageConfiguration = new DataStorageConfiguration();

        //开启持久化
        storageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);

        storageConfiguration.setWalMode(WALMode.LOG_ONLY);

        cfg.setDataStorageConfiguration(storageConfiguration);
    */

        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);

        Ignite ignite = Ignition.start(cfg);
        return ignite;
    }

    private static void testGetPut(Ignite ignite) {
        IgniteCache<String, String> cache = ignite.getOrCreateCache("myCache");

        for (int i = 0; i < 100000; i++) {
            cache.put("mykey_" + i, "myvalue_" + i);
        }
    }

    private static void testAtomOperation(Ignite ignite) {
        IgniteCache<String, Integer> cache = ignite.getOrCreateCache("myCache");

        Integer oldValue = cache.getAndPutIfAbsent("MyKey", 11);
        System.out.println("MyKey: " + oldValue);

        boolean success = cache.putIfAbsent("MyKey", 22);
        System.out.println("MyKey: " + success);

        oldValue = cache.getAndReplace("MyKey", 11);
        System.out.println("MyKey replace: " + oldValue);

        success = cache.replace("MyKey", 22);
        System.out.println("MyKey replace: " + success);

        success = cache.replace("MyKey", 2, 22);
        System.out.println("MyKey replace: " + success);

        success = cache.remove("MyKey", 1);
        System.out.println("MyKey remove: " + success);
    }
}
