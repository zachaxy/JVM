package runtimedata.heap;

import classfile.classconstant.ConstantMethodRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc: 非接口方法引用;
 */
public class MethodRef extends MemberRef {
    Zmethod method;

    public MethodRef(RuntimeConstantPool runtimeConstantPool, ConstantMethodRefInfo methodRefInfo) {
        super(runtimeConstantPool);
        copyMemberRefInfo(methodRefInfo);
    }

    //非接口方法引用转直接引用
    public Zmethod resolvedMethod() {
        if (method == null) {
            resolvedRefMethod();
        }

        return method;
    }

    public void resolvedRefMethod() {
        Zclass d = runtimeConstantPool.clazz;
        //获取 methodRef 所在的类
        Zclass c = resolvedClass();
        if (c.isInterface()) {
            throw new IncompatibleClassChangeError(c.thisClassName);
        }
        //在该类中找到对应的方法
        Zmethod method = lookupMethod(c, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("NoSuchMethodError：" + name);
        }

        if (!method.isAccessTo(d)) {
            throw new IllegalAccessError(d.thisClassName + " can't access " + name + "in Class " + c.thisClassName);
        }

        this.method = method;
    }

    //TODO:需验证方法引用，在父类找不到后，是否需要从其接口中再去找？
    private Zmethod lookupMethod(Zclass c, String name, String descriptor) {
        return MethodLookup.lookupMethodInClass(c, name, descriptor);
    }
}
