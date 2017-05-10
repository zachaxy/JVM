package instructions.constants;

import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:bipush指令从操作数中获取一个byte型整数，扩展成int型，然后推入栈顶
 */
public class BIPUSH implements Instruction {
    int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = reader.readInt8();
    }

    @Override
    public void execute(Zframe frame) {
        // 源码是独到一个int8，然后再用int32将其扩展，那么就变成了实际值。但是在Java中直接扩展还是原值，所以要进行修正在push；
        frame.getOperandStack().pushInt((val + 256) % 256);
    }
}
