package instructions.loads.loadint;

import instructions.base.NoOperandsInstruction;
import instructions.loads.Load;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class ILOAD_0 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Load.iload(frame,0);
    }
}
