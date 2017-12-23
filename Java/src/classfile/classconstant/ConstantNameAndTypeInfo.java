package classfile.classconstant;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 * name_index和descriptor_index都是常量池索引，指向CONSTANT_Utf8_info常量。
 * 字段(成员变量)和方法名就是代码中出现的（或者编译器生成的）字段或方法的名字。
 * 字段或方法名由name_index给出，对应的就是代码中真实的成员变量名或者方法名
 * 字段或方法的描述符由descriptor_index给出
 * 描述符:描述字段的类型,描述方法的参数类型;
 * <p>
 * (1)
 * a:基本类型byte、short、char、int、long、float和double的描述符是单个字母，分别对应B、S、C、I、J、F和D。注意，long的描述符是J而不是L。
 * b:引用类型的描述符是 L ＋ 类的完全限定名 ＋ 分号 eg: Ljava.lang.String;
 * c:数组类型的描述符是[＋数组元素类型描述符。eg: [I  代表int[]
 * <p>
 * (2)字段描述符就是字段类型的描述符。
 * (3)方法描述符是（分号分隔的参数类型描述符）+返回值类型描述符，其中void返回值由单个字母V表示。eg:(Ljava.lang.String;I)Ljava.lang.String
 * 代表的就是 String (String int),方法名由name_index给出;
 */

/*
CONSTANT_NameAndType_info {
    u1 tag;
    u2 name_index;
    u2 descriptor_index;
}
 */
public class ConstantNameAndTypeInfo extends ConstantInfo {
    public int nameIndex;
    public int descriptorIndex;

    public ConstantNameAndTypeInfo(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
    }
}
