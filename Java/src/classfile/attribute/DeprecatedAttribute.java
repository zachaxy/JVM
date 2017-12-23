package classfile.attribute;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:仅起标记作用，不包含任何数据。是JDK1.1引入的，可以出现在 ClassFile、field_info和method_info结构中
 * 属于布尔属性，只有存在和不存在的区别。
 */
public class DeprecatedAttribute extends AttributeInfo {
    int attribute_name_index;
    int attribute_length;

    @Override
    void readInfo(ClassReader reader) {
        //由于没有数据,所以是空的.
    }
}
