package cn.newsuper.Demo;

import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.*;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.values.Values;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.Scanner;

/**
 * Created by Administrator on 2018/3/1.
 * Chronicle 读写测试
 */
public class ChronicleMapTest {
    static private MemoryMXBean memoryMXBean;
    public static void main(String[] args) {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
       Scanner sc = new Scanner(System.in);
        System.out.println("输入测试数据量：");
        final int a= sc.nextInt();
        //int a=10000000;
        //加载初始配置
        final ChronicleMap<LongValue, String> map = mapInit(a);
        write(a,map);
        System.out.println("========================================");
        read(map);
        map.close();

    }

    /***
     * 读取数据
     * @param map
     */
    private static void read(ChronicleMap<LongValue, String> map) {
        final long start = System.currentTimeMillis();
        final long startfreeMemory = Runtime.getRuntime().freeMemory();
        final long startOffHeap = memoryMXBean.getNonHeapMemoryUsage().getUsed();

        for (LongValue value : map.keySet()) {
            map.get(value);
        }
        final long endOffHeap = memoryMXBean.getNonHeapMemoryUsage().getUsed();
        long endfreeMemory=Runtime.getRuntime().freeMemory();
        System.out.println("读取数据时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(startfreeMemory+"--"+endfreeMemory+"读取数据消耗内存："+(endfreeMemory-startfreeMemory)/1024);
        System.out.println("堆外内存最大值"+memoryMXBean.getNonHeapMemoryUsage().getMax());
        System.out.println("堆外内存使用："+(endOffHeap-startOffHeap)/1024);

    }

    /***
     * 写数据
     * @param a
     * @param map
     */
    private static void write(int a, ChronicleMap<LongValue, String> map) {
        final long start = System.currentTimeMillis();
        final long startfreeMemory = Runtime.getRuntime().freeMemory();
        final long startOffHeap = memoryMXBean.getNonHeapMemoryUsage().getUsed();
        for (int i = 0; i< a;i++){
            LongValue key = Values.newHeapInstance(LongValue.class);
            key.setValue(i);
            map.put(key,"aa");
        }
        final long endOffHeap = memoryMXBean.getNonHeapMemoryUsage().getUsed();
        long endfreeMemory=Runtime.getRuntime().freeMemory();
        System.out.println("写入数据时间："+(System.currentTimeMillis()-start)+"ms");
        System.out.println(startfreeMemory+"--"+endfreeMemory+"写入数据消耗内存："+(endfreeMemory-startfreeMemory)/1024);
        System.out.println("堆外内存最大值"+memoryMXBean.getNonHeapMemoryUsage().getMax());
        System.out.println("堆外内存使用："+(endOffHeap-startOffHeap)/1024);
     //   System.out.println("堆外内存使用："+memoryMXBean.getNonHeapMemoryUsage().getUsed()/1024);
    }

    /**
     * 初始配置
     * @param a
     * @return
     */
    public  static ChronicleMap<LongValue,String>  mapInit(int a){
        ChronicleMapBuilder<LongValue,String> mapBuilder = ChronicleMapBuilder.of(LongValue.class,String.class).entries(a).averageValue("aa");
        ChronicleMap<LongValue, String> map = mapBuilder.create();
        return map;
    }
}
