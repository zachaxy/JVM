package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Slots;
import runtimedata.Zframe;
import runtimedata.heap.FieldRef;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZconstantPool;
import runtimedata.heap.Zfield;
import runtimedata.heap.Zmethod;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/7/27.
 * Desc:
 */
public class GET_FIELD extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        Zmethod currentMethod = frame.getMethod();
        Zclass currentClass = currentMethod.getClazz();
        ZconstantPool cp = currentClass.getConstantPool();

        // TODO: 2017/7/26 常量池的转换尚未实现;
        FieldRef fieldRef = null;// cp.getConstant(this.index);
        Zfield field = fieldRef.resolvedField();
        // todo: init class

        if (field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }


        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        Zobject ref = stack.popRef();
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
        Slots slots = ref.getFields();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
                break;
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case 'J':
                stack.pushLong(slots.getLong(slotId));
                break;
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case 'L':
            case '[':
                stack.pushRef(slots.getRef(slotId));
                break;
            default:
                // todo
                break;
        }
    }
}
