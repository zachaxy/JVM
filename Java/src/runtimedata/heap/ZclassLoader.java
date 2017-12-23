package runtimedata.heap;

import java.util.HashMap;

import classfile.ClassFile;
import classfile.classconstant.ConstantDoubleInfo;
import classfile.classconstant.ConstantFloatInfo;
import classfile.classconstant.ConstantIntegerInfo;
import classfile.classconstant.ConstantLongInfo;
import classpath.ClassPath;
import runtimedata.Slots;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: 类加载器
 * TODO:应该是单例的
 */
public class ZclassLoader {
    ClassPath cp;
    HashMap<String, Zclass> map;  //作为缓存，之前加载过这个类，那么就将其class引用保存到map中，后面再用到这个类的时候，直接用map中取；

    public ZclassLoader(ClassPath cp) {
        this.cp = cp;
        this.map = new HashMap<String, Zclass>();
    }

    //go中是将加载的路径的包装类也返回了，目的是为了打印路径信息，这里不需要，如果需要的话，debug即可；
    private byte[] readClass(String name) {
        byte[] data = cp.readClass(name);
        if (data != null) {
            return data;
        } else {
            System.out.println("严重bug！！！");
            return null;
        }
    }

    /*
    * 首先把class文件数据转换成Zclass对象；
    * */
    private Zclass defineClass(byte[] data) {
        Zclass clazz = paraseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        map.put(clazz.thisClassName, clazz);
        return clazz;
    }

    private Zclass paraseClass(byte[] data) {
        ClassFile cf = new ClassFile(data);
        return new Zclass(cf);
    }

    //加载当前类的父类,除非是Object类，否则需要递归调用LoadClass()方法加载它的超类
    //默认情况下,父类和子类的类加载器是同一个;
    private void resolveSuperClass(Zclass clazz) {
        if (!clazz.superClassName.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }


    //加载当前类的接口类
    private void resolveInterfaces(Zclass clazz) {
        int count = clazz.interfaceNames.length;
        clazz.interfaces = new Zclass[count];
        for (int i = 0; i < count; i++) {
            clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
        }
    }

    //先查找classMap，看类是否已经被加载。如果是，直接返回类数据，否则调用loadNonArrayClass（）方法加载类。
    //在类方法中的一个递归调用,也是classLoader中的入口方法
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


    void link(Zclass clazz) {
        verify(clazz);
        prepare(clazz);
    }

    //在执行类的任何代码之前要对类进行严格的检验,这里忽略检验过程,,作为空实现;
    private void verify(Zclass clazz) {
    }

    //给类变量分配空间并赋予初始值
    private void prepare(Zclass clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    // 计算new一个对象所需的空间,单位是clazz.instanceSlotCount,主要包含了类的非静态成员变量(包含父类的)
    private void calcInstanceFieldSlotIds(Zclass clazz) {
        int slotId = 0;
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }

        for (Zfield zfield : clazz.fileds) {
            if (!zfield.classMember.isStatic()) {
                zfield.slotId = slotId;
                slotId++;
                if (zfield.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount = slotId;
    }


//    计算类的静态成员变量所需的空间
    private void calcStaticFieldSlotIds(Zclass clazz) {
        int slotId = 0;
        for (Zfield zfield : clazz.fileds) {
            if (zfield.classMember.isStatic()) {
                zfield.slotId = slotId;
                slotId++;
                if (zfield.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

//    为静态变量申请空间,注意:这个申请空间的过程,就是将所有的静态变量赋值为0或者null;
//    接下来才为 static final 的成员赋初值;
    private void allocAndInitStaticVars(Zclass clazz) {
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Zfield zfield : clazz.fileds) {
            if (zfield.classMember.isStatic() && zfield.classMember.isFinal()) {
                initStaticFinalVar(clazz, zfield);
            }
        }
    }


//    为static final 修饰的成员赋值,这种类型的成员是ConstantXXXInfo类型的,该info中包含真是的值;
    private void initStaticFinalVar(Zclass clazz, Zfield zfield) {
        Slots vars = clazz.staticVars;
        ZconstantPool cp1 = clazz.constantPool;
        int cpIndex = zfield.constValueIndex;
        int slotId = zfield.slotId;

        if (cpIndex > 0) {
            switch (zfield.classMember.getDescriptor()) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    vars.setInt(slotId, ((ConstantIntegerInfo) cp1.getConstant(cpIndex)).getVal());
                    break;
                case "J":
                    vars.setLong(slotId, ((ConstantLongInfo) cp1.getConstant(cpIndex)).getVal());
                    break;
                case "F":
                    vars.setFloat(slotId, ((ConstantFloatInfo) cp1.getConstant(cpIndex)).getVal());
                    break;
                case "D":
                    vars.setDouble(slotId, ((ConstantDoubleInfo) cp1.getConstant(cpIndex)).getVal());
                    break;
                case "Ljava/lang/String;":
//                    TODO:后面实现;
                    throw new RuntimeException("暂时为实现字符串解析");
            }
        }

    }


}
