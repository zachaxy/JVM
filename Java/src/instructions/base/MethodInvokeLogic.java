package instructions.base;

import runtimedata.Slot;
import runtimedata.Zframe;
import runtimedata.Zthread;
import runtimedata.heap.Zmethod;

/**
 * @author zachaxy
 * @date 17/12/27
 */
public class MethodInvokeLogic {

    public static void invokeMethod(Zframe invokerFrame, Zmethod method) {
        Zthread thread = invokerFrame.getThread();
        Zframe newFrame = thread.createFrame(method);
        thread.pushFrame(newFrame);

        int argSlotCount = method.getArgSlotCount();
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = invokerFrame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i,slot);
            }
        }

        //TODO:hack native function,not support yet!
        if (method.isNative()) {
            if ("registerNatives".equals(method.getName())) {
                thread.popFrame();
            } else {
                throw new Error(method.getName() + "is native function,not support yet!");
            }
        }
    }
}
