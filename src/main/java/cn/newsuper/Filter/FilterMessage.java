package cn.newsuper.Filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/13.
 */
public class FilterMessage {
    public static void main(String[] args) {

        String src1 = "ad=nnwiwuuunn-deeee;ap=nnuu88920881;990";
        String re1  = "(?<==)[^=^;]+(?=;)";
        Pattern p = Pattern.compile(re1);
        Matcher m = p.matcher(src1);

        while(m.find()){
            String tmp = m.group();
            System.out.println("结果:"+tmp);
        }
    }
}
