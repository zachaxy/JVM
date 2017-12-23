package classfile.attribute;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:ConstantValue是定长属性，只会出现在field_info结构中。
 * 其作用是通知JVM自动为静态变量赋值。只有被static关键字修饰的变量才有这个属性。
 * 对于以下三种情况：
 * int a1 = 123;
 * static int a2 = 123;
 * final static int a3 = 123;
 *
 * a1是实例变量，其赋值是在实例构造器<init>方法中完成的。
 * 而对于a2和a3，他们都是类变量，那么其赋值有两种情况，一种是在<clinit>,一种是使用ConstantValue属性；
 * 目前Sun Javac 的选择是：a3 使用生成 ConstantValue 属性的方法来赋值
 * a2则将会在<clinit>中进行赋值。
 *
 * 表示常量表达式的值,其在JVM中定义如下:
 * ConstantValue_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 constantvalue_index;
 * }
 * 其中attribute_length的值必须是2。constantvalue_index是常量池索引，代表了常量池中一个字面量常量的引用。
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
