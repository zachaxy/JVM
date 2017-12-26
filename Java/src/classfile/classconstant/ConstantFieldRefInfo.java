package classfile.classconstant;

import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: 字段符号引用
 */
public class ConstantFieldRefInfo extends ConstantMemberRefInfo {
    public ConstantFieldRefInfo(ConstantPool constantPool,int type) {
        super(constantPool,type);
    }
}
