package runtimedata.heap;

import classfile.ConstantValueAttribute;
import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: 根据class文件的字段信息创建字段表，也不在外部单独使用，而是通过makeFields产生的字段数组来访问其中的某一个字段；
 * 所以包含一个索引
 */
public class Zfield {
    ClassMember classMember;
    int constValueIndex;//其赋值在创建构造方法中,代表字节码文件中常量池中的索引,该属性只有在成员有初值的情况下才有;
    int slotId;         //其赋值在运行时常量池的slotId;

    private Zfield(Zclass clazz, MemberInfo cfField) {
        this.classMember = new ClassMember(clazz, cfField);
        copyAttributes(cfField);
    }


    public static Zfield[] makeFields(Zclass zclass, MemberInfo[] cfFields) {
        Zfield[] fields = new Zfield[cfFields.length];
        for (int i = 0; i < fields.length; i++) {
            Zfield field = new Zfield(zclass, cfFields[i]);
            fields[i] = field;
        }
        return fields;
    }


    public void copyAttributes(MemberInfo cfField) {
        ConstantValueAttribute constantValueAttribute = cfField.getConstantValueAttribute();
        if(constantValueAttribute!=null){
            constValueIndex = constantValueAttribute.getConstantValueIndex();
        }
    }

    public boolean isVolatile() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_VOLATILE);
    }

    public boolean isTransient() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_TRANSIENT);
    }

    public boolean isEnum() {
        return 0 != (classMember.accessFlags & AccessFlag.ACC_ENUM);
    }


    public int getConstValueIndex() {
        return constValueIndex;
    }

    public int getSlotId() {
        return slotId;
    }

    public boolean isLongOrDouble() {
        return classMember.getDescriptor().equals("J") || classMember.getDescriptor().equals("D");
    }

    public ClassMember getClassMember() {
        return classMember;
    }
}
