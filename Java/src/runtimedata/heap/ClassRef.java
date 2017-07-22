package runtimedata.heap;

import classfile.ConstantClassInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc: 类引用
 */
public class ClassRef extends SymRef{
    public  ClassRef(ZconstantPool zconstantPool, ConstantClassInfo classInfo){
        this.cp = zconstantPool;
        this.className = classInfo.getName();
    }
}
