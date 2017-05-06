package instructions.control;

import instructions.base.BranchInstruction;
import instructions.base.BranchLogic;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class GOTO extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        BranchLogic.branch(frame,offset);
    }
}
