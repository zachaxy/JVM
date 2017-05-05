package instructions.loads.loaddouble;

import instructions.base.Index8Instruction;
import instructions.loads.Load;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DLOAD extends Index8Instruction {
    @Override
    public void execute(Zframe frame) {
        Load.dload(frame,index);
    }
}
