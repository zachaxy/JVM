package instructions.comparisons.dcmp;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DCMPL extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        DCMP._dcmp(frame, false);
    }
}
