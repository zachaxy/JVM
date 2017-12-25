package runtimedata.heap;

import classfile.classconstant.ConstantMethodRefInfo;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc: 方法引用;
 */
public class MethodRef extends MemberRef {
    Zmethod method;

   public MethodRef(RuntimeConstantPool runtimeConstantPool, ConstantMethodRefInfo methodRefInfo){
       super(runtimeConstantPool);
       copyMemberRefInfo(methodRefInfo);
   }
}
