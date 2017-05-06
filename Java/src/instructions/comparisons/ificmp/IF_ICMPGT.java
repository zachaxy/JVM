package instructions.comparisons.ificmp;

import instructions.base.BranchInstruction;
import instructions.base.BranchLogic;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IF_ICMPGT extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        int[] res = IfIcmp._icmpPop(frame);
        int val1 = res[0];
        int val2 = res[1];
        if (val1 > val2) {
            BranchLogic.branch(frame, offset);
        }
    }
}
