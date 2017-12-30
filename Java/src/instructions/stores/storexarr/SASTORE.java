package instructions.stores.storexarr;

import instructions.base.NoOperandsInstruction;
import instructions.stores.Store;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/29
 * desc:对数组某一元素的赋值  x[0] = y
 */
public class SASTORE extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        //所要赋的值
        short val = (short) operandStack.popInt();
        //数组索引
        int index = operandStack.popInt();
        //数组对象的引用
        Zobject arrRef = operandStack.popRef();

        Store.checkNotNull(arrRef);
        //得到数组对象
        short[] refs = arrRef.getShorts();
        Store.checkIndex(arrRef.getArrayLen(), index);
        //将数组的 index 的元素进行赋值
        refs[index] = val;
    }
}
