package instructions.stores.storeref;

import instructions.base.Index8Instruction;
import instructions.stores.Store;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class ASTORE extends Index8Instruction {
    @Override
    public void execute(Zframe frame) {
        Store.astore(frame,index);
    }
}
