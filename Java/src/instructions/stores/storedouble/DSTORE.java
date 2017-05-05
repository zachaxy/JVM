package instructions.stores.storedouble;

import instructions.base.Index8Instruction;
import instructions.stores.Store;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DSTORE extends Index8Instruction {
    @Override
    public void execute(Zframe frame) {
        Store.dstote(frame,index);
    }
}
