package instructions.comparisons.lcmp;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class LCMP extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        long val2 = stack.popLong();
        long val1 = stack.popLong();
        if (val1 > val2) {
            stack.pushInt(1);
        } else if (val1 == val2) {
            stack.pushInt(0);
        } else {
            stack.pushInt(-1);
        }
    }
}
