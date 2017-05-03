package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public abstract class ConstantInfo {
    static final int CONSTANT_Class = 7;
    static final int CONSTANT_Fieldref = 9;
    static final int CONSTANT_Methodref = 10;
    static final int CONSTANT_InterfaceMethodref = 11;
    static final int CONSTANT_String = 8;
    static final int CONSTANT_Integer = 3;
    static final int CONSTANT_Float = 4;
    static final int CONSTANT_Long = 5;
    static final int CONSTANT_Double = 6;
    static final int CONSTANT_NameAndType = 12;
    static final int CONSTANT_Utf8 = 1;
    static final int CONSTANT_MethodHandle = 15;
    static final int CONSTANT_MethodType = 16;
    static final int CONSTANT_InvokeDynamic = 18;


    //抽象方法来读取信息,需要各自具体类去实现;
    abstract void readInfo(ClassReader reader);


    public static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool constantPool) {
        int tag = (reader.readUint8() + 256) % 256;
        ConstantInfo info = create(tag, constantPool);
        info.readInfo(reader);
        return info;
    }

    private static ConstantInfo create(int tag, ConstantPool constantPool) {
        switch (tag) {
            case CONSTANT_Integer:
                return new ConstantIntegerInfo();
            case CONSTANT_Float:
                return new ConstantFloatInfo();
            case CONSTANT_Long:
                return new ConstantLongInfo();
            case CONSTANT_Double:
                return new ConstantDoubleInfo();
            case CONSTANT_Utf8:
                return new ConstantUtf8Info();
            case CONSTANT_String:
                return new ConstantStringInfo(constantPool);
            case CONSTANT_Class:
                return new ConstantClassInfo(constantPool);
            case CONSTANT_Fieldref:
                return new ConstantMemberRefInfo(constantPool);
            case CONSTANT_Methodref:
                return new ConstantMemberRefInfo(constantPool);
            case CONSTANT_InterfaceMethodref:
                return new ConstantMemberRefInfo(constantPool);
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo();
            // TODO: 2017/5/3 0003 下面三个类还未编码; 
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo();
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo();
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo();
            default:
                throw new RuntimeException("java.lang.ClassFormatError: constant pool tag!");
        }
    }
}


