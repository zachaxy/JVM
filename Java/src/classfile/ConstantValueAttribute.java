package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:ConstantValue是定长属性，只会出现在field_info结构中
 * 表示常量表达式的值,其在JVM中定义如下:
 * ConstantValue_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 constantvalue_index;
 * }
 * 其中attribute_length的值必须是2。constantvalue_index是常量池索引
 */
public class ConstantValueAttribute extends AttributeInfo {

    int constantValueIndex;
    @Override
    void readInfo(ClassReader reader) {
        constantValueIndex = reader.readUint16();
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }
}
