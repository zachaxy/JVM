package classfile.classconstant;

import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: 方法引用消息
 */
public class ConstantMethodRefInfo extends ConstantMemberRefInfo {
    public ConstantMethodRefInfo(ConstantPool constantPool, int type) {
        super(constantPool, type);
    }
}
