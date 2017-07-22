package runtimedata.heap;

import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/20 0020.
 * Desc: 字段和方法都属于类的成员，它们有一些相同的信息（访问标志、名字、描述符）
 * 所以这里定义一个类ClassMember用来存放字段和方法共同的部分；
 * 但是字段和方法不同的部分还需要分开处理:Zfiled;Zmethod
 */
public class ClassMember {
    int accessFlags;    //访问标示
    String name;        //字段、方法名称
    private String descriptor;  //字段、方法描述
    Zclass clazz;       //所属的类，这样可以通过字段或方法访问到它所属的类

    public ClassMember(Zclass clazz, MemberInfo cfField) {
        copyMemberInfo(cfField);
        this.clazz = clazz;
    }

    /**
     * 从class文件的memberInfo中复制数据
     * @param memberInfo
     */
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
