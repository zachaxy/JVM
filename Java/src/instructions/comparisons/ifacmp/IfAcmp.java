package instructions.comparisons.ifacmp;

import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IfAcmp {
    public static boolean _acmp(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        Zobject ref2 = stack.popRef();
        Zobject ref1 = stack.popRef();
        return ref1 == ref2;
    }
}
