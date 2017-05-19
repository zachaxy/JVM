package instructions.extended;

import instructions.base.BranchInstruction;
import instructions.base.BranchLogic;
import runtimedata.Zframe;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IFNONNULL extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        Zobject ref = frame.getOperandStack().popRef();
        if (ref != null) {
            BranchLogic.branch(frame, offset);
        }
    }
}
