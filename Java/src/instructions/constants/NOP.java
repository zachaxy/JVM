package instructions.constants;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: nop指令是最简单的一条指令，因为它什么也不做
 */
public class NOP extends NoOperandsInstruction{
    @Override
    public void execute(Zframe frame) {

    }
}
