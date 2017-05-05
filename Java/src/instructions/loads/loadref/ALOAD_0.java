package instructions.loads.loadref;

import instructions.base.NoOperandsInstruction;
import instructions.loads.Load;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class ALOAD_0 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Load.aload(frame,0);
    }
}
