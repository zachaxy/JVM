package instructions.references;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/29
 * desc:获取数组长度的指令
 */
public class ARRAY_LENGTH extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        Zobject arrRef = operandStack.popRef();
        if (arrRef == null) {
            throw new NullPointerException("called length on a null Object");
        }
        int arrLen = arrRef.getArrayLen();
        operandStack.pushInt(arrLen);
    }
}
