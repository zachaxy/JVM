package instructions.comparisons.dcmp;

import instructions.base.NoOperandsInstruction;
import instructions.comparisons.fcmp.FCMP;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DCMPG extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        DCMP._dcmp(frame, true);
    }
}
