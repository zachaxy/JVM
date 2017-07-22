package runtimedata.heap;

import classfile.ConstantFieldRefInfo;
import classfile.ConstantMethodRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc: 方法引用;
 */
public class MethodRef extends MemberRef {
    Zmethod method;

   public MethodRef(ZconstantPool zconstantPool, ConstantMethodRefInfo methodRefInfo){
       super(zconstantPool);
       copyMemberRefInfo(methodRefInfo);
   }
}
