package runtimedata.heap;

import classfile.classconstant.ConstantInterfaceMethodRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc:接口方法引用
 */
public class InterfaceMethodRef extends MemberRef {
    public InterfaceMethodRef(ZconstantPool zconstantPool, ConstantInterfaceMethodRefInfo interfaceMethodRefInfo) {
        super(zconstantPool);
        copyMemberRefInfo(interfaceMethodRefInfo);
    }
}
