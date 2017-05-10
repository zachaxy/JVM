package instructions.constants;

import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:sipush指令从操作数中获取一个short型整数，扩展成int型，然后推入栈顶
 */
public class SIPUSH implements Instruction {
    int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = reader.readInt16();
    }

    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().pushInt((val + 65536) % 65536);
    }
}
