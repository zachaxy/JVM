package instructions.references;

import instructions.base.Index8Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZclassLoader;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/29
 * desc：创建基本类型的数组的指令，一维的！
 */
public class NEW_ARRAY extends Index8Instruction {
    //Array Type  atype
    private final int AT_BOOLEAN = 4;
    private final int AT_CHAR = 5;
    private final int AT_FLOAT = 6;
    private final int AT_DOUBLE = 7;
    private final int AT_BYTE = 8;
    private final int AT_SHORT = 9;
    private final int AT_INT = 10;
    private final int AT_LONG = 11;

    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        //从栈中获取数组的大小
        int count = operandStack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("" + count);
        }
        ZclassLoader loader = frame.getMethod().getClazz().getLoader();
        Zclass arrClazz = getPrimitiveArrayClass(loader);
        Zobject arr = arrClazz.newArray(count);
        operandStack.pushRef(arr);
    }

    //获取基本类型数组的class;如果没有加载过,需要加载进JVM
    private Zclass getPrimitiveArrayClass(ZclassLoader loader) {
        //从字节码中获取到的 index 表明的是哪种类型的数组
        switch (this.index) {
            case AT_BOOLEAN:
                return loader.loadClass("[Z");
            case AT_BYTE:
                return loader.loadClass("[B");
            case AT_CHAR:
                return loader.loadClass("[C");
            case AT_SHORT:
                return loader.loadClass("[S");
            case AT_INT:
                return loader.loadClass("[I");
            case AT_LONG:
                return loader.loadClass("[J");
            case AT_FLOAT:
                return loader.loadClass("[F");
            case AT_DOUBLE:
                return loader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }
}


