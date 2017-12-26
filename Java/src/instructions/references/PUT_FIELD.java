package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.*;


/**
 * Author: zhangxin
 * Time: 2017/7/27.
 * Desc: 给实例变量赋值,所赋的值保存在操作数栈中;
 * 和静态变量赋值不同的是:静态变量在class中,而实例变量在每一个对象中,该对象也在当前操作数栈;
 */
public class PUT_FIELD extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        Zmethod currentMethod = frame.getMethod();
        Zclass currentClass = currentMethod.getClazz();
        RuntimeConstantPool runtimeConstantPool = currentClass.getRuntimeConstantPool();

        //首先获取到fieldRef引用;
        FieldRef fieldRef = (FieldRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        //根据引用获取到字段;
        Zfield field = fieldRef.resolvedField();
        Zclass clazz = field.getClazz();

        //NOTE:其实是可以通过实例访问类静态变量的，但这样无谓的增加了编译器解析的成本，因此这里直接抛出异常
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError("should not call a static field by an instance");
        }

        if (field.isFinal()) {
            if (currentClass != clazz || "<clinit>".equals(currentMethod.getName())) {
                throw new IllegalAccessError(field.getName()+" can't be assigned out of instance");
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        Zobject instance;
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I': {
                int val = stack.popInt();
                instance = stack.popRef();
                if (instance == null) {
                    throw new NullPointerException("call "+field.getName()+" on a null object");
                }
                instance.getFields().setInt(slotId, val);
                break;
            }
            case 'F': {
                float val = stack.popFloat();
                instance = stack.popRef();
                if (instance == null) {
                    throw new NullPointerException("call "+field.getName()+" on a null object");
                }
                instance.getFields().setFloat(slotId, val);
                break;
            }
            case 'J': {
                long val = stack.popLong();
                instance = stack.popRef();
                if (instance == null) {
                    throw new NullPointerException("call "+field.getName()+" on a null object");
                }
                instance.getFields().setLong(slotId, val);
                break;
            }
            case 'D': {
                double val = stack.popDouble();
                instance = stack.popRef();
                if (instance == null) {
                    throw new NullPointerException("call "+field.getName()+" on a null object");
                }
                instance.getFields().setDouble(slotId, val);
                break;
            }
            case 'L':
            case '[': {
                Zobject val = stack.popRef();
                instance = stack.popRef();
                if (instance == null) {
                    throw new NullPointerException("call "+field.getName()+" on a null object");
                }
                instance.getFields().setRef(slotId, val);
                break;
            }
            default:
                break;
        }
    }
}

