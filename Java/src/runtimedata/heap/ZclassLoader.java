package runtimedata.heap;

import classfile.ClassFile;
import classpath.ClassPath;
import runtimedata.Slots;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: 类加载器
 */
public class ZclassLoader {
    ClassPath classPath;
    HashMap<String, Zclass> map;  //作为缓存，之前加载过这个类，那么就将其class引用保存到map中，后面再用到这个类的时候，直接用map中取；

    public ZclassLoader(ClassPath classPath) {
        this.classPath = classPath;
        this.map = new HashMap<String, Zclass>();

        loadBasicClasses();
        loadPrimitiveClasses();
    }

    private void loadBasicClasses() {
        //经过这一步load之后,classMap中就有Class的Class了，已经Object 和 Class 所实现的接口；
        Zclass jlClassClass = loadClass("java/lang/Class");
        //接下来对classMap中的每一个Class都创建一个jClass;使用jlClassClass.NewObject()方法;
        // 通过调用 newObject 方法，为每一个 Class 都创建一个元类对象；这样在使用 String.class 时可以直接获取到；
        for (Map.Entry<String, Zclass> entry : map.entrySet()) {
            Zclass jClass = entry.getValue();
            if (jClass.jObject == null) {
                jClass.jObject = jlClassClass.newObject();
                jClass.jObject.extra = jClass;
            }
        }
    }

    //加载基本类型的类:void.class;boolean.class;byte.class
    private void loadPrimitiveClasses() {
        for (Map.Entry<String, String> entry : ClassNameHelper.primitiveTypes.entrySet()) {
            String className = entry.getKey();
            loadPrimitiveClass(className);
        }
    }

    //加载基本类型,和数组类似,也没有对应的class文件,只能在运行时创建;基本类型:无超类,也没有实现任何接口
    /* 针对基本类型的三点说明：
    1. void和基本类型的类型名字就是：void，int，float 等
    2. 基本类型的类没有超类，也没有实现任何接口
    3. 非基本类型的类对象是通过 ldc 指令加载到操作数栈中的
    */
    private void loadPrimitiveClass(String className) {
        Zclass clazz = new Zclass(AccessFlag.ACC_PUBLIC, className, this, true,
                null,
                new Zclass[]{});
        clazz.jObject = map.get("java/lang/Class").newObject();
        clazz.jObject.extra = clazz;
        map.put(className, clazz);
    }

    //先查找classMap，看类是否已经被加载。如果是，直接返回类数据，否则调用loadNonArrayClass（）方法加载类。
    //在类方法中的一个递归调用,也是classLoader中的入口方法
    public Zclass loadClass(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }

        Zclass clazz;
        if (name.charAt(0) == '[') {
            clazz = loadArrayClass(name);
        } else {
            clazz = loadNonArrayClass(name);
        }

        //为每一个 class 都关联一个元类
        Zclass jlClassClass = map.get("java/lang/Class");
        if (jlClassClass != null) {
            clazz.jObject = jlClassClass.newObject();
            clazz.jObject.extra = clazz;
        }
        return clazz;
    }

    //数组类的字节码不是从 class 文件中获取的，而是在加载了基本类型之后，在 JVM 中动态创建的
    private Zclass loadArrayClass(String name) {
        Zclass clazz = new Zclass(AccessFlag.ACC_PUBLIC, name, this, true,
                loadClass("java/lang/Object"),
                new Zclass[]{loadClass("java/lang/Cloneable"), loadClass("java/io/Serializable")});
        map.put(name, clazz);
        return clazz;
    }

    private Zclass loadNonArrayClass(String name) {
        byte[] data = readClass(name);
        Zclass clazz = defineClass(data);
        link(clazz);
//        System.out.println("[Loaded  " + name + " from  " + name + "]");//因为我们没有返回加载的路径，所以这里只好以name来代替了；
        return clazz;
    }

    /**
     * 利用 ClassPath 把 class 文件读进来
     *
     * @param name 类名，eg：java.lang.String 或者包含 main 方法的主类名
     * @return class 字节数据
     */
    private byte[] readClass(String name) {
        byte[] data = classPath.readClass(name);
        if (data != null) {
            return data;
        } else {
            throw new ClassCastException("class name: " + name);
        }
    }


    /*
    * 首先把class文件数据转换成 ClassFile 对象，在转为 Zclass 对象；
    * 加载父类
    * 加载接口
    * resolveSuperClass：是一个递归的过程，不断的加载父类信息
    * */
    private Zclass defineClass(byte[] data) {
        Zclass clazz = parseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        map.put(clazz.thisClassName, clazz);
        return clazz;
    }

    private Zclass parseClass(byte[] data) {
        ClassFile cf = new ClassFile(data);
        return new Zclass(cf);
    }

    //加载当前类的父类,除非是Object类，否则需要递归调用LoadClass()方法加载它的超类
    //默认情况下,父类和子类的类加载器是同一个;
    private void resolveSuperClass(Zclass clazz) {
        if (!"java/lang/Object".equals(clazz.thisClassName)) {
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


    private void link(Zclass clazz) {
        verify(clazz);
        prepare(clazz);
    }

    //在执行类的任何代码之前要对类进行严格的检验,这里忽略检验过程,作为空实现;
    private void verify(Zclass clazz) {
    }

    //给类变量分配空间并赋予初始值
    private void prepare(Zclass clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    // 计算new一个对象所需的空间,单位是clazz.instanceSlotCount,主要包含了类的非静态成员变量(包含父类的)
    // 但是这里并没有真正的申请空间，只是计算大小，同时为每个非静态变量关联 slotId
    private void calcInstanceFieldSlotIds(Zclass clazz) {
        int slotId = 0;
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }

        for (Zfield field : clazz.fileds) {
            if (!field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount = slotId;
    }


    //计算类的静态成员变量所需的空间，不包含父类，同样也只是计算和分配 slotId，不申请空间
    private void calcStaticFieldSlotIds(Zclass clazz) {
        int slotId = 0;
        for (Zfield field : clazz.fileds) {
            if (field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    // 为静态变量申请空间,注意:这个申请空间的过程,就是将所有的静态变量赋值为0或者null;
    // 如果是 static final 的基本类型或者 String，其值会保存在ConstantValueAttribute属性中
    private void allocAndInitStaticVars(Zclass clazz) {
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Zfield field : clazz.fileds) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }


    // 为static final 修饰的成员赋值,这种类型的成员是ConstantXXXInfo类型的,该info中包含真正的值;
    private void initStaticFinalVar(Zclass clazz, Zfield zfield) {
        Slots staticVars = clazz.staticVars;
        RuntimeConstantPool runtimeConstantPool = clazz.getRuntimeConstantPool();
        int index = zfield.constValueIndex;
        int slotId = zfield.slotId;

        if (index > 0) {
            switch (zfield.getDescriptor()) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    int intValue = (int) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setInt(slotId, intValue);
                    break;
                case "J":
                    long longValue = (long) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setLong(slotId, longValue);
                    break;
                case "F":
                    float floatValue = (float) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setFloat(slotId, floatValue);
                    break;
                case "D":
                    double doubleValue = (double) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    staticVars.setDouble(slotId, doubleValue);
                    break;
                case "Ljava/lang/String;":
                    String stringValue = (String) runtimeConstantPool.getRuntimeConstant(index).getValue();
                    Zobject jStr = StringPool.jString(clazz.getLoader(), stringValue);
                default:
                    break;
            }
        }

    }


}
