package instructions.math.rem;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class LREM extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        long val2 = stack.popLong();
        if (val2 == 0) {
            throw new ArithmeticException("java.lang.ArithmeticException: / by zero");
        }
        long val1 = stack.popLong();
        long res = val1 % val2;
        stack.pushLong(res);
    }
}

