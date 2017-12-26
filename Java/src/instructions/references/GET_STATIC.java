package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Slots;
import runtimedata.Zframe;
import runtimedata.heap.FieldRef;
import runtimedata.heap.Zclass;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zfield;
import runtimedata.heap.Zmethod;

/**
 * Author: zhangxin
 * Time: 2017/7/26.
 * Desc:获取静态变量的值，将其值放在操作数栈中
 */
public class GET_STATIC extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        Zmethod currentMethod = frame.getMethod();
        Zclass currentClass = currentMethod.getClazz();
        RuntimeConstantPool runtimeConstantPool = currentClass.getRuntimeConstantPool();

        FieldRef fieldRef = (FieldRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        Zfield field = fieldRef.resolvedField();
        Zclass clazz = field.getClazz();
        // TODO:class的初始化未实现
        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError("can't access unstatic field: " + field.getName());
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots slots = clazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();

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
                break;
        }
    }
}
