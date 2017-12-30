package instructions.loads;

import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 工具类,为不同的数据类型提供不同的加载机制;
 * 总体原则是:先从本地变量表中取出变量,然后将该变量压入到操作数栈中;
 */
public class Load {
    public static void aload(Zframe frame, int index) {
        Zobject ref = frame.getLocalVars().getRef(index);
        frame.getOperandStack().pushRef(ref);
    }

    public static void dload(Zframe frame, int index) {
        double val = frame.getLocalVars().getDouble(index);
        frame.getOperandStack().pushDouble(val);
    }

    public static void fload(Zframe frame, int index) {
        float val = frame.getLocalVars().getFloat(index);
        frame.getOperandStack().pushFloat(val);
    }

    public static void iload(Zframe frame, int index) {
        int val = frame.getLocalVars().getInt(index);
        frame.getOperandStack().pushInt(val);
    }

    public static void lload(Zframe frame, int index) {
        long val = frame.getLocalVars().getLong(index);
        frame.getOperandStack().pushLong(val);
    }

    //用在 load 数组元素时，检测数组是否为 null
    public static void checkNotNull(Zobject arrRef) {
        if (arrRef == null) {
            throw new NullPointerException();
        }
    }

    public static void checkIndex(int count, int index) {
        if (index < 0 || index >= count) {
            throw new ArrayIndexOutOfBoundsException("index: " + index + " array's count: " + count);
        }
    }
}
