package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.Zframe;

/**
 * @author zachaxy
 * @date 17/12/26
 */
public class INVOKE_SPECIAL extends Index16Instruction{
    @Override
    public void execute(Zframe frame) {
        //TODO:hack 构造方法；仅作为测试用，后续会实现
        frame.getOperandStack().popRef();
    }
}
