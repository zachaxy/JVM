package instructions.constants;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: push null
 */
public class ACONST_NULL extends NoOperandsInstruction {

    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().pushRef(null);
    }
}
