package runtimedata.heap;

import classfile.MemberInfo;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc:
 */
public class Zfield {
    ClassMember classMember;
    int constValueIndex;
    int slotId;

    public Zfield(Zclass clazz, MemberInfo cfField) {
        this.classMember = new ClassMember(clazz, cfField);
        // TODO: 2017/5/20 0020  还有 两个字段没有赋值 
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
//        cfField.ConstantValueAttribute();
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
        return classMember.descriptor.equals("J") || classMember.descriptor.equals("D");
    }
}
