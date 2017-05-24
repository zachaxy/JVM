package runtimedata.heap;

import classfile.ClassFile;
import classpath.ClassPath;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc:
 */
public class ZclassLoader {
    ClassPath cp;
    HashMap<String, Zclass> map;  //作为缓存，之前加载过这个类，那么就将其class引用保存到map中，后面再用到这个类的时候，直接用map中取；

    public ZclassLoader(ClassPath cp) {
        this.cp = cp;
        this.map = new HashMap<String, Zclass>();
    }

    //go中是将加载的路径的包装类也返回了，目的是为了打印路径信息，这里不需要，如果需要的话，debug即可；
    byte[] readClass(String name) {
        byte[] data = cp.readClass(name);
        if (data != null) {
            return data;
        } else {
            System.out.println("严重bug！！！");
            return null;
        }
    }

    //先查找classMap，看类是否已经被加载。如果是，直接返回类数据，否则调用loadNonArrayClass（）方法加载类。
    public Zclass loadClass(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }

        return loadNonArrayClass(name);
    }

    private Zclass loadNonArrayClass(String name) {
        byte[] data = readClass(name);
        Zclass clazz = defineClass(data);
        link(clazz);
        System.out.println("[Loaded  " + name + " from  " + name + "]");//因为我们没有返回加载的路径，所以这里只好以name来代替了；
        return clazz;
    }

    /*
    * 首先把class文件数据转换成Zclass对象；
    * */
    Zclass defineClass(byte[] data) {
        Zclass clazz = paraseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        map.put(clazz.thisClassName, clazz);
        return clazz;
    }

    //加载当前类的接口类
    private void resolveInterfaces(Zclass clazz) {
        int count = clazz.interfaceNames.length;
        clazz.interfaces = new Zclass[count];
        for (int i = 0; i < count; i++) {
            clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
        }
    }

    //加载当前类的父类,除非是Object类，否则需要递归调用LoadClass()方法加载它的超类
    private void resolveSuperClass(Zclass clazz) {
        if (!clazz.superClassName.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }

    private Zclass paraseClass(byte[] data) {
        ClassFile cf = new ClassFile(data);
        return new Zclass(cf);
    }

    void link(Zclass clazz) {
        verify(clazz);
        prepare(clazz);
    }

    private void verify(Zclass clazz) {
        // TODO: 2017/5/24 0024
    }

    private void prepare(Zclass clazz) {

    }


}
