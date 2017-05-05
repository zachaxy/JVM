package instructions.constants;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:Push double
 */
public class DCONST_0 extends NoOperandsInstruction {

    @Override
    public void execute(Zframe frame) {
        frame.getOperandStack().pushDouble(0.0);
    }
}
