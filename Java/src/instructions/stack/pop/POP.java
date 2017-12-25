package instructions.stack.pop;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: pop指令把栈顶变量弹出
 * 只能用于弹出int、float等占用一个操作数栈位置的变量
 */
public class POP extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().popSlot();
    }
}
