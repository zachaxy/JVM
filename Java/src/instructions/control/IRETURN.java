package instructions.control;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;
import runtimedata.Zthread;

/**
 * @author zachaxy
 * @date 17/12/27
 * desc:返回值为 int 的 return 指令
 * 执行方法在执行结束后，如果有返回值，其返回值会放在该方法的操作数栈
 * 执行方法的外部——调用方法，需要将执行方法的返回值，压入调用方法的操作数栈
 */
public class IRETURN extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        Zthread thread = frame.getThread();
        Zframe currentFrame = thread.popFrame();
        Zframe invokerFrame = thread.getCurrentFrame();
        int val = currentFrame.getOperandStack().popInt();
        invokerFrame.getOperandStack().pushInt(val);
    }
}
