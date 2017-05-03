package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:
 */

/*  属性表在JVM中的定义;
attribute_info {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 info[attribute_length];
}
*/
public abstract class AttributeInfo {
    ConstantPool constantPool;
    ClassReader reader;

    abstract void readInfo(ClassReader reader);

    //读取单个属性
    private static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool) {
        int attrNameIndex = reader.readUint16();
        String attrName = constantPool.getUtf8(attrNameIndex);
        int attrLen = ByteUtils.byteToInt32(reader.readUint32());
        AttributeInfo attrInfo = create(attrName, attrLen, constantPool);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    //读取属性表;这个和ConstantPool中的方法类似,一般都是一下全部读取出来,不会只读一个
    public static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readUint16();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantPool);
        }
        return attributes;
    }

    //Java虚拟机规范预定义了23种属性，先解析其中的8种
    /*
    23种预定义属性可以分为三组。
    第一组属性是实现Java虚拟机所必需的，共有5种；
    第二组属性是Java类库所必需的，共有12种；
    第三组属性主要提供给工具使用，共有6种。第三组属性是可选的，也就是说可以不出现在class文件中。
    (如果class文件中存在第三组属性，Java虚拟机实现或者Java类库也是可以利用它们的，比如使用LineNumberTable属性在异常堆栈中显示行号。)
     */
    private static AttributeInfo create(String attrName, int attrLen, ConstantPool constantPool) {
        if (attrName.equals("Code")) {
            return new CodeAttribute(constantPool);
        }/*else if (attrName.equals("ConstantValue")){
            return new ConstantValueAttribute();
        }else if (attrName.equals("Deprecated")){
            return new DeprecatedAttribute();
        }else if (attrName.equals("Exceptions")){
            return new ExceptionsAttribute();
        }else if (attrName.equals("LineNumberTable")){
            return new LineNumberTableAttribute();
        }else if (attrName.equals("LocalVariableTable")){
            return new LocalVariableTableAttribute();
        }else if (attrName.equals("SourceFile")){
            return new SourceFileAttribute(constantPool);
        }else if (attrName.equals("Synthetic")){
            return new SyntheticAttribute();
        }*/ else {
            return new UnparsedAttribute(attrName, attrLen);
        }

    }
}
