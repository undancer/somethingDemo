package demo;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * User: undancer
 * Date: 13-3-4
 * Time: 下午6:58
 */
public class Demo {

    static String url = "http://kj.edu24ol.com/update/qa/qa1.txt";

    static char[] magicChars = {'j', 'a', 'G', 'g', '8', 'r', 'D', 'f', 'U', 'b', 'W'};    //权重表

    static Comparator<String> customComparator = new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            if (o1.length() != o2.length()) {                   //字符长度不一致，则按字符数比较，可以将末位的行序号移动到前面来
                return o1.length() - o2.length();
            }
            char[] chs1 = o1.toCharArray();
            char[] chs2 = o2.toCharArray();
            for (int index = 0; index < chs1.length; index++) { //逐字符比较
                char c1 = chs1[index];
                char c2 = chs2[index];

                int c1_off = ArrayUtils.indexOf(magicChars, c1);
                int c2_off = ArrayUtils.indexOf(magicChars, c2);

                if (c1_off >= 0 && c2_off >= 0) {               //均为魔法字符则按字符权重比较
                    return c1_off - c2_off;
                } else if (c1 != c2) {                          //正常比较
                    return c1 - c2;
                }
            }
            return 0;                                           //相等
        }
    };

    public static List<String> readLines(URL url) {            // JAVA7语法
        try (InputStream is = url.openStream()) {
            return IOUtils.readLines(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    public static void main(String[] args) {
        try {
            List<String> lines = readLines(new URL(url));                       //从服务器获取数据
            for (String line : lines) {                                         //逐行处理
                String[] elements = StringUtils.split(line, ' ');               //拆分元素
                Arrays.sort(elements, customComparator);                        //排序
                System.out.println(StringUtils.join(elements, ' '));            //组合元素
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
