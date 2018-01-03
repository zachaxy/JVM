package runtimedata.heap;

import classfile.attribute.CodeAttribute;

/**
 * @author zachaxy
 * @date 18/1/2
 */
public class ExceptionTable {
    private ExceptionHandler[] exceptionTable;

    public ExceptionTable(CodeAttribute.ExceptionTableEntry[] entry, RuntimeConstantPool runtimeConstantPool) {
        exceptionTable = new ExceptionHandler[entry.length];
        for (int i = 0; i < entry.length; i++) {
            exceptionTable[i] = new ExceptionHandler();
            exceptionTable[i].startPc = entry[i].getStartPc();
            exceptionTable[i].endPc = entry[i].getEndPc();
            exceptionTable[i].handlerPc = entry[i].getHandlerPc();
            exceptionTable[i].catchType = getCatchType(entry[i].getCatchType(), runtimeConstantPool);
        }
    }

    //将classFile中的异常类型(符号引用)转换为运行时的直接引用
    public ClassRef getCatchType(int index, RuntimeConstantPool runtimeConstantPool) {
        if (index == 0) {
            // catch all
            return null;
        }
        return (ClassRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
    }

    //返回能解决当前Exception的handler=>多个catch块,决定用哪个
    public ExceptionHandler findExceptionHandler(Zclass exClazz, int pc) {
        for (int i = 0; i < exceptionTable.length; i++) {
            ExceptionHandler handler = exceptionTable[i];
            if (pc >= handler.startPc && pc < handler.endPc) {
                // catch all
                if (handler.catchType == null) {
                    return handler;
                }
                // 如果catch 的异常是实际抛出的异常的父类，也可以捕获
                Zclass catchClazz = handler.catchType.resolvedClass();
                if (catchClazz == exClazz || catchClazz.isSuperClassOf(exClazz)) {
                    return handler;
                }
            }
        }
        return null;
    }
}

class ExceptionHandler {
    int startPc;
    int endPc;
    int handlerPc;
    ClassRef catchType;
}