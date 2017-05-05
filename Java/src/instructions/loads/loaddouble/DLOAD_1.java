package instructions.loads.loaddouble;

import instructions.base.NoOperandsInstruction;
import instructions.loads.Load;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DLOAD_1 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Load.dload(frame,1);
    }
}
