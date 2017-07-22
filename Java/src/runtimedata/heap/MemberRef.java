package runtimedata.heap;

import classfile.ConstantMemberRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc: 字段和方法的符号引用保存的相同信息;
 */
public class MemberRef {
    SymRef symRef;
    String name;
    String descriptor;

    public MemberRef(ZconstantPool zconstantPool){
        symRef = new SymRef();
        symRef.cp = zconstantPool;
    }

    void copyMemberRefInfo(ConstantMemberRefInfo refInfo){
        symRef.className = refInfo.getClassName();
        name = refInfo.getName();
        descriptor = refInfo.getDescriptor();
    }
}
