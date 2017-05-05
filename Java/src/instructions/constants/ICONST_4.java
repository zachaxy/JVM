package instructions.constants;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: Push int constant
 */
public class ICONST_4 extends NoOperandsInstruction {

    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().pushInt(4);
    }
}
