package classfile.attribute;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: Exceptions是变长属性，记录方法抛出的异常表
 */

/*
Exceptions_attribute {
u2 attribute_name_index;
u4 attribute_length;
u2 number_of_exceptions;
u2 exception_index_table[number_of_exceptions];
}
*/

public class ExceptionsAttribute extends AttributeInfo {

    int[] exceptionIndexTable;

    @Override
    void readInfo(ClassReader reader) {
        exceptionIndexTable = reader.readUint16s();
    }

    public int[] getExceptionIndexTable() {
        return exceptionIndexTable;
    }
}
