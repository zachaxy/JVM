package classpath;

import java.io.File;
import java.io.IOException;

/**
 * Author: zhangxin
 * Time: 2017/5/1 0001.
 * Desc:
 */
public class ClassPath {
    //分别存放三种类路径
    Entry bootClasspath;
    Entry extClasspath;
    Entry userClasspath;

    //parse()函数使用 -Xjre 选项解析启动类路径和扩展类路径
    // 使用-classpath/-cp选项解析用户类路径

    public ClassPath(String jreOption, String cpOption) {
        parseBootAndExtClasspath(jreOption);
        parseUserClasspath(cpOption);
    }


    //这里参数掺进来的是: C:\Program Files\Java\jdk1.8.0_20\jre
    void parseBootAndExtClasspath(String jreOption) {
        String jreDir = getJreDir(jreOption);

        //可能出现的情况是: jre/lib/*
        String jreLibPath = jreDir + File.separator + "lib" + File.separator + "*";
        bootClasspath = new WildcardEntry(jreLibPath);

        //可能出现的情况是: jre/lib/ext/*
        String jreExtPath = jreDir + File.separator + "lib" + File.separator + "ext" + File.separator + "*";
        extClasspath = new WildcardEntry(jreExtPath);
    }

    String getJreDir(String jreOption) {
        File jreFile;
        if (jreOption != null && jreOption != "") {
            jreFile = new File(jreOption);
            if (jreFile.exists()) {
                return jreOption;
            }
        }

        jreFile = new File("jre");
        if (jreFile.exists()) {
            return jreFile.getAbsolutePath();
        }

        String java_home = System.getenv("JAVA_HOME");
        if (java_home != null) {
            return java_home + File.separator + "jre";
        }

        throw new RuntimeException("Can not find jre folder!");
    }

    void parseUserClasspath(String cpOption) {
        userClasspath = Entry.createEntry(cpOption);
    }

    public byte[] readClass(String className) {
        className = className + ".class";
        byte[] data;
        try {
            data = bootClasspath.readClass(className);
            if (data != null) {
                return data;
            }

            data = extClasspath.readClass(className);
            if (data != null) {
                return data;
            }

            data = userClasspath.readClass(className);
            if (data != null) {
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("can't find class!");
    }

    @Override
    public String toString() {
        return userClasspath.printClassName();
    }
}
