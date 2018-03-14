package cn.newsuper.Demo;


import net.openhft.chronicle.core.values.LongValue;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import net.openhft.chronicle.map.ExternalMapQueryContext;
import net.openhft.chronicle.map.MapEntry;
import net.openhft.chronicle.values.Values;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


/**
 * Created by Administrator on 2018/3/1.
 */
public class Demo1 {
    @Test
    public void test1() {
        ChronicleMapBuilder<LongValue,Person> mapBuilder = ChronicleMapBuilder.of(LongValue.class,Person.class).entries(2);
        ChronicleMap<LongValue,Person> map = mapBuilder.create();
        LongValue key = Values.newHeapInstance(LongValue.class);
        Person value = Values.newHeapInstance(Person.class);
        key.setValue(11);
        value.setId(520);
        value.setName(11);
        map.put(key,value);
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void test2(){
        ChronicleMapBuilder<String,LongValue> mapBuilder = ChronicleMapBuilder.of(String.class,LongValue.class).averageKey("my").name("test").entries(2);
        ChronicleMap<String,LongValue> map = mapBuilder.create();
        LongValue value = Values.newHeapInstance(LongValue.class);
        value.setValue(115155115);
        map.put("da",value);
        map.put("s",value);
        map.put("das",value);
        map.put("wode",value);
        for (String s : map.keySet()) {
            System.out.println(map.get(s)+s);
        }
    }
    File file = new File("sssss");

    public void  test3() throws IOException {
        ChronicleMap<String,LongValue> map = ChronicleMap.of(String.class,LongValue.class).averageKey("my").entries(5).createOrRecoverPersistedTo(file,false);
        ExternalMapQueryContext<String,LongValue,?> c=map.queryContext("");
        MapEntry<String,LongValue> en = c.entry();
        LongValue ss = en.value().get();
    }
}
