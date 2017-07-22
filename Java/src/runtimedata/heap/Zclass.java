package runtimedata.heap;

import classfile.ClassFile;
import runtimedata.Slot;
import runtimedata.Slots;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc:
 */
public class Zclass {
    int accessFlags;        // 表示当前类的访问标志
    String thisClassName;   //当前类名字(完全限定名)
    String superClassName;  //父类名字(完全限定名)
    String[] interfaceNames;//接口名字(完全限定名,不可以为null,若为实现接口,数组大小为0)
    ZconstantPool constantPool;//运行时常量池,注意和class文件中常量池区别;
    Zfield[] fileds;        //字段表
    Zmethod[] methods;      //方法表
    ZclassLoader loader;    //类加载器
    Zclass superClass;      //当前类的父类class,由类加载时,给父类赋值;
    Zclass[] interfaces;    //当前类的接口class,由类加载时,给父类赋值;
    int instanceSlotCount;  // 实例变量所占空间大小
    int staticSlotCount;    // 类变量所占空间大小
    Slots staticVars;      // 存放静态变量

    public Zclass(ClassFile classFile) {
        accessFlags = classFile.getAccessFlags();
        thisClassName = classFile.getClassName();
        superClassName = classFile.getSuperClassName();
        interfaceNames = classFile.getInterfaceNames();
        // FIXME: 2017/7/22 这里没有对classFile的常量池进行转换,而是直接拿过来用了
        constantPool = new ZconstantPool(this,classFile.getConstantPool());
        fileds = Zfield.makeFields(this,classFile.getFields());
        methods = Zmethod.makeMethods(this,classFile.getMethods());

    }


    public boolean isPublic() {
        return 0 != (accessFlags & AccessFlag.ACC_PUBLIC);
    }

    public boolean isFinal() {
        return 0 != (accessFlags & AccessFlag.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (accessFlags & AccessFlag.ACC_SUPER);
    }

    public boolean isInterface() {
        return 0 != (accessFlags & AccessFlag.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (accessFlags & AccessFlag.ACC_ABSTRACT);
    }

    public boolean isSynthetic() {
        return 0 != (accessFlags & AccessFlag.ACC_SYNTHETIC);
    }

    public boolean isAnnotation() {
        return 0 != (accessFlags & AccessFlag.ACC_ANNOTATION);
    }

    public boolean isEnum() {
        return 0 != (accessFlags & AccessFlag.ACC_ENUM);
    }
}
