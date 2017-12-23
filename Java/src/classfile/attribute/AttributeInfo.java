package classfile.attribute;

import Utils.ByteUtils;
import classfile.ClassReader;
import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:
 */

/*  属性表在JVM中的定义;
虚拟机规范中,每个属性都定义了name_index,用来从常量池中拿到属性名;
attr_len,用来定义属性的的长度,便于接下来的解读
其实很多属性的长度都是已知的,
不确定长度的有:code属性,其长度需要根据len来读取;
attribute_info {
    u2 attribute_name_index;
    u4 attribute_length;
    u1 info[attribute_length];
}
*/
public abstract class AttributeInfo {

    //抽象方法,由各属性自己读取对应的属性信息
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
    /*fixme: 各属性中没有传入attrName和attrLen,只有对应的value;
    23种预定义属性可以分为三组。
    第一组属性是实现Java虚拟机所必需的，共有5种；
    第二组属性是Java类库所必需的，共有12种；
    第三组属性主要提供给工具使用，共有6种。第三组属性是可选的，也就是说可以不出现在class文件中。
    (如果class文件中存在第三组属性，Java虚拟机实现或者Java类库也是可以利用它们的，比如使用LineNumberTable属性在异常堆栈中显示行号。)
     */
    private static AttributeInfo create(String attrName, int attrLen, ConstantPool constantPool) {
        if ("Code".equals(attrName)) {
            return new CodeAttribute(constantPool);
        }else if ("ConstantValue".equals(attrName)){
            return new ConstantValueAttribute();
        }else if ("Deprecated".equals(attrName)){
            return new DeprecatedAttribute();
        }else if ("Exceptions".equals(attrName)){
            return new ExceptionsAttribute();
        }else if ("LineNumberTable".equals(attrName)){
            return new LineNumberTableAttribute();
        }else if ("LocalVariableTable".equals(attrName)){
            return new LocalVariableTableAttribute();
        }else if ("SourceFile".equals(attrName)){
            return new SourceFileAttribute(constantPool);
        }else if ("Synthetic".equals(attrName)){
            return new SyntheticAttribute();
        } else {
            return new UnparsedAttribute(attrName, attrLen);
        }

    }
}
