package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ClassFile {

    //这里使用char,是想使用16位无符号整数
    int minorVersion;
    int majorVersion;
    //    constantPool ConstantPool;
    char accessFlags;
    char thisClass;
    char superClass;
    char[] interfaces;


    ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        read(reader);
    }

    void read(ClassReader reader) {
        readAndCheckMagic(reader);
        readAndCheckVersion(reader);
    }


    //文件开头前四个字节,是魔数,因为是64的无符号的,所以不能简单的使用long来表示,目前的解决方法是将4个字节的byte数组转换为字符串,对比class文件的魔数
    private void readAndCheckMagic(ClassReader reader) {
        String magic = ByteUtils.bytesToHexString(reader.readUint32());
        if (!magic.equals("CAFEBABE")) {
            throw new RuntimeException("java.lang.ClassFormatError: magic!");
        }
    }

    //版本号,16字节,分别代表主版本号和次版本号,并向前兼容
    private void readAndCheckVersion(ClassReader reader) {
        minorVersion = reader.readUint16();
        majorVersion = reader.readUint16();
        if (majorVersion == 45) {
            return;
        }
        if (minorVersion == 0 && majorVersion >= 46 && majorVersion <= 52) {
            return;
        }
        throw new RuntimeException("java.lang.UnsupportedClassVersionError!");
    }
}
