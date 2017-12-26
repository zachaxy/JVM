package classfile.classconstant;

import classfile.ClassReader;
import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc: 常量的抽象类，这里实现了常量池中所有常量的类型。
 * 由于常量池中存放的信息各不相同，所以每种常量的格式也不同。常量数据的第一字节是tag，用来区分常量类型。
 * 根据在常量池的字节码的每个tag字节，可以判断下一个常量类型是什么，每个常量占用多少个字节都是可以确定的，接着再读取下一个tag，确定下一个常量类型；
 */
public abstract class ConstantInfo {
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_NameAndType = 12;
    public static final int CONSTANT_MethodHandle = 15;
    public static final int CONSTANT_MethodType = 16;
    public static final int CONSTANT_InvokeDynamic = 18;


    //抽象方法来读取信息,需要各自具体类去实现;因为每种常量所占的字节数并不相同。
    abstract void readInfo(ClassReader reader);

    //表明当前常量的类型是上述常量的哪一种;
    protected int type;

    public int getType() {
        return type;
    }

    public static ConstantInfo readConstantInfo(ClassReader reader, ConstantPool constantPool) {
        int type = (reader.readUint8() + 256) % 256;
        ConstantInfo info = create(type, constantPool);
        info.readInfo(reader);
        return info;
    }

    private static ConstantInfo create(int type, ConstantPool constantPool) {
        switch (type) {
            case CONSTANT_Utf8:
                return new ConstantUtf8Info(1);
            case CONSTANT_Integer:
                return new ConstantIntegerInfo(3);
            case CONSTANT_Float:
                return new ConstantFloatInfo(4);
            case CONSTANT_Long:
                return new ConstantLongInfo(5);
            case CONSTANT_Double:
                return new ConstantDoubleInfo(6);
            case CONSTANT_String:
                return new ConstantStringInfo(constantPool, 8);
            case CONSTANT_Class:
                return new ConstantClassInfo(constantPool, 7);
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(constantPool, 9);
            case CONSTANT_Methodref:
                return new ConstantMethodRefInfo(constantPool, 10);
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(constantPool, 11);
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(12);
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo(16);
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo(15);
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo(18);
            default:
                throw new RuntimeException("java.lang.ClassFormatError: constant pool tag!");
        }
    }
}


