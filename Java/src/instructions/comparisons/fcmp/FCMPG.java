package instructions.comparisons.fcmp;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class FCMPG extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        FCMP._fcmp(frame, true);
    }
}
