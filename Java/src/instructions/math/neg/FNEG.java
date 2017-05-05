package instructions.math.neg;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class FNEG extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        float val = stack.popFloat();
        stack.pushFloat(-val);
    }
}