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


/**
 * Author: zhangxin
 * Time: 2017/7/26.
 * Desc:
 */
public class PUT_STATIC extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        Zmethod currentMethod = frame.getMethod();
        Zclass currentClass = currentMethod.getClassMember().getClazz();
        ZconstantPool cp = currentClass.getConstantPool();

        // TODO: 2017/7/26 常量池的转换尚未实现;
        FieldRef fieldRef = null;// cp.getConstant(this.index);
        Zfield field = fieldRef.resolvedField();
        Zclass clazz = field.getClassMember().getClazz();
        // todo: init class

        if (!field.getClassMember().isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        if (field.getClassMember().isFinal()) {
            if (currentClass != clazz || "<clinit>".equals(currentMethod.getClassMember().getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        String descriptor = field.getClassMember().getDescriptor();
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
                // todo
                break;
        }
    }
}
