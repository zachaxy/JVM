package classfile.attribute;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: 用于描述栈帧中局部变量表中的变量和Java源码中定义的变量之间的关系。
 * 这并不是运行时必须的属性，但默认会生成到Class文件中，可以在Javac 中使用 -g：none 来取消这项信息；
 * 如果不生成这项，产生的影响是：当其他人引用这个方法时，IDE将会使用诸如arg0，arg1之类的占位符代替原来的参数名，但对运行毫无影响。
 * 只是在调试期间无法根据参数名从上下文中获得参数值。
 */
public class LocalVariableTableAttribute extends AttributeInfo {
    LocalVariableTableEntry[] localVariableTable;

    @Override
    void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            localVariableTable[i] = new LocalVariableTableEntry(
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16()
            );
        }
    }

    static class LocalVariableTableEntry {
        int startPc;    //代表该局部变量的生命周期开始的字节码偏移量
        int length;     //代表该局部变量的作用范围所覆盖的长度
        int nameIndex;    //指向常量池中个CONSTANT_Utf8_info型常量的索引，代表局部变量名称
        int descriptorIndex;    //指向常量池中个CONSTANT_Utf8_info型常量的索引，变量描述符
        int index;      //该局部变量在栈帧局部变量包中slot的位置

        public LocalVariableTableEntry(int startPc, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }
    }
}
