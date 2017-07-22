package runtimedata.heap;

import classfile.ConstantFieldRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22
 * Desc: 字段引用
 */
public class FieldRef extends MemberRef {
    Zfield field;

    public FieldRef(ZconstantPool zconstantPool, ConstantFieldRefInfo fieldRefInfo){
        super(zconstantPool);
        copyMemberRefInfo(fieldRefInfo);
    }
}
