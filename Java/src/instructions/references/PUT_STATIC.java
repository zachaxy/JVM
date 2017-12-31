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

        if (field.isFinal()) {
            if (currentClass != clazz || !"<clinit>".equals(currentMethod.getName())) {
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
