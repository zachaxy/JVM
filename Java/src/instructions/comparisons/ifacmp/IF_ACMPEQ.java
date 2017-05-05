package instructions.comparisons.ifacmp;

import instructions.base.BranchInstruction;
import instructions.base.BranchLogic;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IF_ACMPEQ extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        if (IfAcmp._acmp(frame)) {
            BranchLogic.branch(frame, offset);
        }
    }
}
