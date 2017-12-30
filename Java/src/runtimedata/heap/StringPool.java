package runtimedata.heap;

import java.util.HashMap;

/**
 * @author zachaxy
 * @date 17/12/30
 * desc:这里用来模拟 JVM 中的字符串池，但是由于当前的 JVM 本身就是用 Java 写的，所以会省掉很多真正的细节
 * 这里用一个 HasMap 来模拟字符串池，key 为从 class 文件中读到的字符串，value 为我们定义的 Zobject
 */
public class StringPool {
    public static HashMap<String, Zobject> internedStrings = new HashMap<>();
    public static HashMap<Zobject, String> realInternedStrings = new HashMap<>();

    public static Zobject jString(ZclassLoader loader, String str) {
        if (internedStrings.containsKey(str)) {
            return internedStrings.get(str);
        }

//        chars := stringToUtf16(goStr)
//        jChars := &Object{loader.LoadClass("[C"), chars, nil}
//
//        //这里创建了一个String的对象
//        jStr := loader.LoadClass("java/lang/String").NewObject()
//        //不是通过构造方法,而是通过直接setVar的方式,将上面创建的jChar数组设置进来;
//        jStr.SetRefVar("value", "[C", jChars)
//
//        internedStrings[goStr] = jStr
//        return jStr
        char[] chars = str.toCharArray();
        Zobject jChars = new Zobject(loader.loadClass("[C"), chars, null);

        Zobject jStr = loader.loadClass("java/lang/String").newObject();
        jStr.setRefVar("value", "[C", jChars);
        internedStrings.put(str, jStr);
        //这一步的实现有些取巧了，Zobject 并没有实现 equals 和 hashCode 方法，但依然可以作为 key
        //是因为在 internedStrings 中的 key 是 java 中的String，这是合法的，相同的 String 取到的 value
        //也就是 Zobject ，也是一样的；这就保证了 Zobject 可以作为hashMap 的 key；
        realInternedStrings.put(jStr, str);
        return jStr;
    }

    //凡是调用该方法，必定是从上面的常量池中获取了相同的字符串，然后返回其在 JVM 中的 Zobject
    public static String realString(Zobject jStr) {
        if (realInternedStrings.containsKey(jStr)) {
            return realInternedStrings.get(jStr);
        }

        Zobject ref = jStr.getRefVar("value", "[C");
        char[] chars = ref.getChars();
        String realStr = new String(chars);
        realInternedStrings.put(jStr, realStr);
        return realStr;
    }
}
