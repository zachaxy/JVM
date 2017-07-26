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
public class PUT_FIELD extends Index16Instruction {
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

        if (field.getClassMember().isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        if (field.getClassMember().isFinal()) {
            if (currentClass != clazz || "<clinit>".equals(currentMethod.getClassMember().getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        String descriptor = field.getClassMember().getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();

        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I': {
                int val = stack.popInt();
                Zobject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setInt(slotId, val);
                break;
            }
            case 'F': {
                float val = stack.popFloat();
                Zobject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setFloat(slotId, val);
                break;
            }
            case 'J':{
                long val = stack.popLong();
                Zobject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setLong(slotId, val);
                break;
            }
            case 'D':{
                double val = stack.popDouble();
                Zobject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setDouble(slotId, val);
                break;
            }
            case 'L':
            case '[':{
                Zobject val = stack.popRef();
                Zobject ref = stack.popRef();
                if (ref == null) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getFields().setRef(slotId, val);
                break;
            }
            default:
                // todo
                break;
        }
    }
}

