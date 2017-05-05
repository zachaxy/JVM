package instructions.stores.storeref;

import instructions.base.NoOperandsInstruction;
import instructions.stores.Store;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class ASTORE_0 extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Store.astore(frame,0);
    }
}
