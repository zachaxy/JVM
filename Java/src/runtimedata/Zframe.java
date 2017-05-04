package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:
 */
public class Zframe {
    Zframe lower;       //当前帧的 前一帧的引用;
    LocalVars localVars;
    OperandStack operandStack;

    // TODO: 2017/5/4 0004
    public Zframe(int maxLocals, int maxStack) {
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
