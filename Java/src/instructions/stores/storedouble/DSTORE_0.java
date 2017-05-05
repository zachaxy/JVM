package instructions.stores.storedouble;

import instructions.base.NoOperandsInstruction;
import instructions.stores.Store;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DSTORE_0 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Store.dstote(frame,0);
    }
}
