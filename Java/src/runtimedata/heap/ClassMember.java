package runtimedata.heap;

import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/20 0020.
 * Desc: 字段和方法都属于类的成员，它们有一些相同的信息（访问标志、名字、描述符）
 * 所以这里定义一个父类ClassMember用来存放字段和方法共同的部分；
 * 但是字段和方法不同的部分还需要分开处理:Zfiled;Zmethod
 */
public class ClassMember {
    protected int accessFlags;    //访问标示
    protected String name;        //字段、方法名称
    protected String descriptor;  //字段、方法描述
    protected Zclass clazz;       //所属的类，这样可以通过字段或方法访问到它所属的类

    public ClassMember(Zclass clazz, MemberInfo classFileMemberInfo) {
        copyMemberInfo(classFileMemberInfo);
        this.clazz = clazz;
    }

    /**
     * 从class文件的memberInfo中复制数据
     *
     * @param memberInfo
     */
    private void copyMemberInfo(MemberInfo memberInfo) {
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

    public boolean isAccessTo(Zclass d) {
        if (isPublic()) {
            return true;
        }

        if (isProtected()) {
            return d == clazz || d.isSubClassOf(clazz) || d.getPackageName().equals(clazz.getPackageName());
        }

        if (!isPrivate()) {
            return d.getPackageName().equals(clazz.getPackageName());
        }

        return d == clazz;
    }

    public int getAccessFlags() {
        return accessFlags;
    }


    public void setClazz(Zclass clazz) {
        this.clazz = clazz;
    }
}
