package classfile.classconstant;

import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: 接口方法引用消息
 */
public class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo {
    public ConstantInterfaceMethodRefInfo(ConstantPool constantPool, int type) {
        super(constantPool, type);
    }
}
