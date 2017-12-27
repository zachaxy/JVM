package instructions.control;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;
import runtimedata.Zthread;

/**
 * @author zachaxy
 * @date 17/12/27
 * desc:返回值为 long 的 return 指令
 */
public class LRETURN extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Zthread thread = frame.getThread();
        Zframe currentFrame = thread.popFrame();
        Zframe invokerFrame = thread.getCurrentFrame();
        long val = currentFrame.getOperandStack().popLong();
        invokerFrame.getOperandStack().pushLong(val);
    }
}
