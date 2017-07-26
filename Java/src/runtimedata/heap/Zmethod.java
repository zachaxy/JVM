package runtimedata.heap;

import classfile.CodeAttribute;
import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: Method对象不单独使用，而是在使用了makeMethods之后，得到一个Method对象，然后在使用对用的方法；
 * 该对象比Zfield要复杂的多，因为其还有方法内的字节码
 */
public class Zmethod {
    ClassMember classMember;
    int maxStack;
    int maxLocals;
    byte[] code;        //如果没有code属性,取值为null;不过就算是空方法也有一个return 语句;

    private Zmethod(Zclass clazz, MemberInfo cfMethod) {
        this.classMember = new ClassMember(clazz, cfMethod);
        copyAttributes(cfMethod);
    }

    //该方法用来初始化成员变量：maxStack，maxLocals，code
    private void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttribute = cfMethod.getCodeAttribute();
        if (codeAttribute != null) {
            maxStack = codeAttribute.getMaxStack();
            maxLocals = codeAttribute.getMaxLocals();
            code = codeAttribute.getCode();
        }
    }


    public static Zmethod[] makeMethods(Zclass zclass, MemberInfo[] cfMethods) {
        Zmethod[] methods = new Zmethod[cfMethods.length];
        for (int i = 0; i < methods.length; i++) {
            Zmethod method = new Zmethod(zclass, cfMethods[i]);
            methods[i] = method;
        }
        return methods;
    }


    public boolean isSynchronized() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_BRIDGE);
    }

    public boolean isVarargs() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_VARARGS);
    }

    public boolean isNative() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_NATIVE);
    }

    public boolean isAbstract() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_ABSTRACT);
    }

    public boolean isStrict() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_STRICT);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public ClassMember getClassMember() {
        return classMember;
    }
}
