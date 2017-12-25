package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.Zclass;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/7/26.
 * Desc:
 */
public class INSTANCE_OF extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        Zobject ref = stack.popRef();
        if (ref == null) {
            stack.pushInt(0);
            return;
        }

        RuntimeConstantPool cp = frame.getMethod().getClazz().getRuntimeConstantPool();
        ClassRef classRef = null;// cp.getConstant(this.index);
        Zclass clazz = classRef.resolvedClass();
        if (ref.isInstanceOf(clazz)) {
            stack.pushInt(1);
        } else {
            stack.pushInt(0);
        }
    }
}
