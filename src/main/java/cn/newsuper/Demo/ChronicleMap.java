package cn.newsuper.Demo;

import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;

/**
 * Created by Administrator on 2018/3/1.
 */
public class ChronicleMap<L, S> {
    static private MemoryMXBean memoryMXBean;
    public static void main(String[] args) {
        memoryMXBean = ManagementFactory.getMemoryMXBean();

        System.out.println(memoryMXBean.getNonHeapMemoryUsage().getMax());
    }
}
