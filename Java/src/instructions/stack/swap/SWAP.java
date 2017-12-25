package instructions.stack.swap;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Slot;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: swap指令交换栈顶的两个变量
 */
public class SWAP extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot1 = stack.popSlot();
        Slot slot2 = stack.popSlot();

        stack.pushSlot(slot1);
        stack.pushSlot(slot2);
    }
}
