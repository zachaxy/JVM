package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:Code是变长属性，只存在于method_info结构中
 */

/*
JVM 中 Code 属性的定义;
Code_attribute {
u2 attribute_name_index;
u4 attribute_length;
u2 max_stack;
u2 max_locals;
u4 code_length;
u1 code[code_length];
u2 exception_table_length;
{ u2 start_pc;
u2 end_pc;
u2 handler_pc;
u2 catch_type;
} exception_table[exception_table_length];
u2 attributes_count;
attribute_info attributes[attributes_count];
}
*/
public class CodeAttribute extends AttributeInfo {
    ConstantPool constantPool;
    int maxStack;       //操作数栈的最大深度
    int maxLocals;      //局部变量表大小
    byte[] code;        //字节码
    ExceptionTableEntry[] exceptionTable;   //
    AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool) {
this.constantPool = constantPool;
    }

    @Override
    void readInfo(ClassReader reader) {
        maxStack = reader.readUint16();
        maxLocals = reader.readUint16();
        int codeLength = ByteUtils.byteToInt32(reader.readUint32());
        code = reader.readBytes(codeLength);
        exceptionTable = readExceptionTable(reader);
        attributes = readAttributes(reader, constantPool);
    }

    private ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
        int exceptionTableLength = reader.readUint16();
        ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i++) {
            exceptionTable[i] = new ExceptionTableEntry(reader.readUint16(), reader.readUint16(),
                    reader.readUint16(), reader.readUint16());
        }
        return exceptionTable;
    }


    static class ExceptionTableEntry {
        int startPc;
        int endPc;
        int handlerPc;
        int catchType;

        public ExceptionTableEntry(int startPc, int endPc, int handlerPc, int catchType) {
            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.catchType = catchType;
        }
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }
}
