package classpath;

import java.io.File;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:
 */
public abstract class Entry {
    public static final String pathListSeparator = File.separator;

    /**
     * 负责寻找和加载class文件
     *
     * @param className class文件的相对路径，路径之间用斜线 / 分隔，文件名有.class后缀
     */
    abstract void readClass(String className);

    /**
     * @return 返回className的字符串表示形式;
     */
    abstract String printClassName();

}
