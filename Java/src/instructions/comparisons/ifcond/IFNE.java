package instructions.comparisons.ifcond;

import instructions.base.BranchInstruction;
import instructions.base.BranchLogic;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IFNE extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        int val = frame.getOperandStack().popInt();
        if (val != 0) {
            BranchLogic.branch(frame, offset);
        }
    }
}
