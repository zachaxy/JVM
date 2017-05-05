package instructions.loads.loadref;

import instructions.base.Index8Instruction;
import instructions.loads.Load;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class ALOAD extends Index8Instruction{
    @Override
    public void execute(Zframe frame) {
        Load.aload(frame,index);
    }
}
