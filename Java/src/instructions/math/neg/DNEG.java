package instructions.math.neg;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DNEG extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        double val = stack.popDouble();
        stack.pushDouble(-val);
    }
}
