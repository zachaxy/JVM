package runtimedata.heap;

import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/20 0020.
 * Desc: 字段和方法都属于类的成员，它们有一些相同的信息（访问标志、名字、描述符），所以这里定义一个类ClassMember用来存放字段和方法共同的部分；
 */
public class ClassMember {
    int accessFlags;
    String name;
    String descriptor;
    Zclass clazz;

    public ClassMember(Zclass clazz, MemberInfo cfField) {
        copyMemberInfo(cfField);
        this.clazz = clazz;
    }

    void copyMemberInfo(MemberInfo memberInfo) {
        accessFlags = memberInfo.getAccessFlags();
        name = memberInfo.getName();
        descriptor = memberInfo.getDescriptor();
    }


    public boolean isPublic() {
        return 0 != (accessFlags & AccessFlag.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (accessFlags & AccessFlag.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (accessFlags & AccessFlag.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (accessFlags & AccessFlag.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (accessFlags & AccessFlag.ACC_FINAL);
    }

    public boolean isSynthetic() {
        return 0 != (accessFlags & AccessFlag.ACC_SYNTHETIC);
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public Zclass getClazz() {
        return clazz;
    }

}
