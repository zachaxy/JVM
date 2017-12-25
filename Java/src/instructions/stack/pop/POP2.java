package instructions.stack.pop;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: double和long变量在操作数栈中占据两个位置，需要使用pop2指令弹出
 */
public class POP2 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        stack.popSlot();
        stack.popSlot();
    }
}
