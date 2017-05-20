package runtimedata.heap;

import classfile.ClassFile;
import runtimedata.Slot;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc:
 */
public class Zclass {
    int accessFlags;
    String thisClassName;
    String superClassName;
    String[] interfaceNames;
    ZconstantPool constantPool;
    Zfield[] fileds;
    Zmethod[] methods;
    ZclassLoader loader;
    Zclass superClass;
    Zclass[] interfaces;
    int instanceSlotCount;  // 实例变量所占空间大小
    int staticSlotCount;    // 类变量所占空间大小
    Slot[] staticVars;      // 存放静态变量

    public Zclass(ClassFile classFile) {
        accessFlags = classFile.getAccessFlags();
        thisClassName = classFile.getClassName();
        superClassName = classFile.getSuperClassName();
        interfaceNames = classFile.getInterfaceNames();
//        constantPool = new ZconstantPool(this,classFile.getConstantPool());
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
