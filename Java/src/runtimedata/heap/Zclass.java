package runtimedata.heap;

import classfile.ClassFile;
import classfile.ConstantPool;
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
    ConstantPool constantPool;
    Zfield[] fileds;
    Zmethod[] methods;
    ZclassLoader loader;
    Zclass superClass;
    Zclass[] interfaces;
    int instanceSlotCount;
    int staticSlotCount;
    Slot[] staticVars;

    public Zclass(ClassFile classFile) {
        accessFlags = classFile.getAccessFlags();
        thisClassName = classFile.getClassName();
        superClassName = classFile.getSuperClassName();
        interfaceNames = classFile.getInterfaceNames();
//        constantPool = new ConstantPool();
//        fileds = Zfield.getFileds(this,classFile.getFields());
//        methods = Zmethod.getMethods(this,classFile.getMethods());

    }


    public boolean isPublic()  {
        return 0 != (accessFlags&AccessFlag.ACC_PUBLIC);
    }
    public boolean isFinal()  {
        return 0 != (accessFlags&AccessFlag.ACC_FINAL);
    }
    public boolean isSuper()  {
        return 0 != (accessFlags&AccessFlag.ACC_SUPER);
    }
    public boolean isInterface()  {
        return 0 != (accessFlags&AccessFlag.ACC_INTERFACE);
    }
    public boolean isAbstract()  {
        return 0 != (accessFlags&AccessFlag.ACC_ABSTRACT);
    }
    public boolean isSynthetic()  {
        return 0 != (accessFlags&AccessFlag.ACC_SYNTHETIC);
    }
    public boolean isAnnotation()  {
        return 0 != (accessFlags&AccessFlag.ACC_ANNOTATION);
    }
    public boolean isEnum()  {
        return 0 != (accessFlags&AccessFlag.ACC_ENUM);
    }
}
