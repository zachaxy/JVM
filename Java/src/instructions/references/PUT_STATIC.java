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
 * Desc: 为静态变量赋值，所赋的值在操作数栈中
 */
public class PUT_STATIC extends Index16Instruction {
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

        if (field.isFinal()) {
            if (currentClass != clazz || "<clinit>".equals(currentMethod.getName())) {
                throw new IllegalAccessError("java.lang.IllegalAccessError");
            }
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
                slots.setInt(slotId, stack.popInt());
                break;
            case 'F':
                slots.setFloat(slotId, stack.popFloat());
                break;
            case 'J':
                slots.setLong(slotId, stack.popLong());
                break;
            case 'D':
                slots.setDouble(slotId, stack.popDouble());
            case 'L':
            case '[':
                slots.setRef(slotId, stack.popRef());
                break;
            default:
                break;
        }
    }
}
