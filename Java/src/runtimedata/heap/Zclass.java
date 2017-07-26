package runtimedata.heap;

import classfile.ClassFile;
import runtimedata.Slots;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: 如何设法保证同一个对象的 class == 返回 true
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
        constantPool = new ZconstantPool(this, classFile.getConstantPool());
        fileds = Zfield.makeFields(this, classFile.getFields());
        methods = Zmethod.makeMethods(this, classFile.getMethods());

    }

    public ZconstantPool getConstantPool() {
        return constantPool;
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

    public boolean isAccessibleTo(Zclass other) {
        return isPublic() || getPackageName().equals(other.getPackageName());
    }

    public String getPackageName() {
        int i = thisClassName.lastIndexOf("/");
        if (i > 0) {
            return thisClassName.substring(0, i);
        }
        return "";
    }

    public boolean isSubClassOf(Zclass iface) {
        for (Zclass c = superClass; c != null; c = c.superClass) {
            if (c == iface) {
                return true;
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(Zclass iface) {
        for (Zclass superInterface : interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }


    public boolean isImplements(Zclass iface) {
        for (Zclass c = this; c != null; c = c.superClass) {
            for (int i = 0; i < c.interfaces.length; i++) {
                if (c.interfaces[i] == iface || c.interfaces[i].isSubInterfaceOf(iface)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isAssignableFrom(Zclass other) {
        if (other == this) {
            return true;
        }

        if (!this.isInterface()) {
            return other.isSubClassOf(this);
        } else {
            return other.isImplements(this);
        }
    }

    public Zobject newObject() {
        return new Zobject(this);
    }
}
