package instructions.references;

import instructions.base.ClassInitLogic;
import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Slots;
import runtimedata.Zframe;
import runtimedata.heap.*;

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

        //判断其Class是否已经加载过,如果还未加载,那么调用其类的<clinit>方法压栈
        if (!clazz.isInitStarted()) {
            //当前指令已经是在执行new了,但是类还没有加载,所以当前帧先回退,让类初始化的帧入栈,等类初始化完成后,重新执行new;
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }

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
