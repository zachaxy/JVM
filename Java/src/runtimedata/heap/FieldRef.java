package runtimedata.heap;

import classfile.classconstant.ConstantFieldRefInfo;

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

    public Zfield resolvedField(){
        if (field == null){
            resolvedRefField();
        }

        return field;
    }

    public void resolvedRefField(){
        Zclass d = symRef.cp.clazz;
        Zclass c = symRef.resolvedClass();

        Zfield field = lookupField(c,name,descriptor);
        if (field == null){
            throw new RuntimeException("java.lang.NoSuchFieldError");
        }

        if (!field.classMember.isAccessTo(d)){
            throw  new RuntimeException("java.lang.IllegalAccessError");
        }

        this.field = field;
    }

    private Zfield lookupField(Zclass c, String name, String descriptor) {
        for (int i = 0; i < c.fileds.length; i++) {

        }

        for (Zfield zf:c.fileds) {
            if (zf.classMember.name.equals(name) && zf.classMember.getDescriptor().equals(descriptor)){
                return zf;
            }
        }

        for (Zclass zin:c.interfaces) {
            for (Zfield zf:zin.fileds){
                if (zf.classMember.name.equals(name) && zf.classMember.getDescriptor().equals(descriptor)){
                    return zf;
                }
            }
        }

        if (c.superClass!=null){
            return lookupField(c.superClass,name,descriptor);
        }

        return null;
    }

}
