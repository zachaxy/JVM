package runtimedata.heap;

import classfile.classconstant.ConstantInterfaceMethodRefInfo;

import static runtimedata.heap.MethodLookup.LookupMethodInInterfaces;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc:接口方法引用
 */
public class InterfaceMethodRef extends MemberRef {
    Zmethod method;

    public InterfaceMethodRef(RuntimeConstantPool runtimeConstantPool, ConstantInterfaceMethodRefInfo interfaceMethodRefInfo) {
        super(runtimeConstantPool);
        copyMemberRefInfo(interfaceMethodRefInfo);
    }

    //接口方法引用转直接引用
    public Zmethod ResolvedInterfaceMethod() {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        Zclass d = runtimeConstantPool.clazz;
        //获取 methodRef 所在的接口
        Zclass c = resolvedClass();
        if (!c.isInterface()) {
            throw new IncompatibleClassChangeError(c.thisClassName);
        }
        //在该类中找到对应的方法
        Zmethod method = lookupInterfaceMethod(c, name, descriptor);
        if (method == null) {
            throw new NoSuchMethodError("NoSuchMethodError：" + name);
        }

        if (!method.isAccessTo(d)) {
            throw new IllegalAccessError(d.thisClassName + " can't access " + name + "in Class " + c.thisClassName);
        }

        this.method = method;
    }

    private Zmethod lookupInterfaceMethod(Zclass iface, String name, String descriptor) {
//        for _, method := range iface.methods {
//            if method.name == name && method.descriptor == descriptor {
//                return method
//            }
//        }
//
//        return lookupMethodInInterfaces(iface.interfaces, name, descriptor)
        for (Zmethod method : iface.methods) {
            if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                return method;
            }
        }
        return LookupMethodInInterfaces(iface.interfaces, name, descriptor);
    }
}
