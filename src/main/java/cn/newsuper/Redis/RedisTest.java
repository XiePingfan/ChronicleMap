package cn.newsuper.Redis;

import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/12.
 * Redis数据库压力测试
 */
public class RedisTest {
    public static void main(String[] args) {
        //配置redis连接
        Jedis jedis = new Jedis("192.168.25.144",6379);
        //键盘输入
        Scanner sc = new Scanner(System.in);
        System.out.println("输入测试数据：");
        final int num = sc.nextInt();
        //获取初始值
        final long start = System.currentTimeMillis();
        final long startfreeMemory = Runtime.getRuntime().freeMemory();

        //插入数据
        for (int i =0;i<num;i++){
            jedis.set(i+"","aa");
        }
        long endfreeMemory=Runtime.getRuntime().freeMemory();
        System.out.println("写入数据时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(startfreeMemory+"--"+endfreeMemory+"写入数据消耗内存："+(endfreeMemory-startfreeMemory)/1024);


        //获取初始值
        final long start1 = System.currentTimeMillis();
        final long startfreeMemory1 = Runtime.getRuntime().freeMemory();
        //查询数据
        final Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            jedis.get(key);
        }

        long endfreeMemory1=Runtime.getRuntime().freeMemory();
        System.out.println("读取数据时间："+(System.currentTimeMillis()-start1)+"ms");
        System.out.println(startfreeMemory+"--"+endfreeMemory+"读取数据消耗内存："+(endfreeMemory1-startfreeMemory1)/1024);
       // System.out.println("清除缓存成功"+jedis.flushAll());

    }

}
