package instructions.control;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;
import runtimedata.Zthread;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/27
 * desc:返回值为 实例对象 的 return 指令
 */
public class ARETURN extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Zthread thread = frame.getThread();
        Zframe currentFrame = thread.popFrame();
        Zframe invokerFrame = thread.getCurrentFrame();
        Zobject val = currentFrame.getOperandStack().popRef();
        invokerFrame.getOperandStack().pushRef(val);
    }
}
