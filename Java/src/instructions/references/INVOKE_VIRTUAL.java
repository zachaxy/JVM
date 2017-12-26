package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.MethodRef;
import runtimedata.heap.RuntimeConstantPool;

/**
 * @author zachaxy
 * @date 17/12/26
 */
public class INVOKE_VIRTUAL extends Index16Instruction{
    @Override
    public void execute(Zframe frame) {
        //TODO:hack println 方法；仅作为测试用，后续会实现
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        if ("println".equals(methodRef.getName())){
            OperandStack operandStack = frame.getOperandStack();
            switch (methodRef.getDescriptor()){
                case "(Z)V":
                    System.out.println(operandStack.popInt()==1);
                    break;
                case "(C)V":
                    System.out.println(operandStack.popInt());
                    break;
                case "(B)V":
                    System.out.println(operandStack.popInt());
                    break;
                case "(S)V":
                    System.out.println(operandStack.popInt());
                    break;
                case "(I)V":
                    System.out.println(operandStack.popInt());
                    break;
                case "(J)V":
                    System.out.println(operandStack.popLong());
                    break;
                case "(F)V":
                    System.out.println(operandStack.popFloat());
                    break;
                case "(D)V":
                    System.out.println(operandStack.popLong());
                    break;
                default:
                    break;
            }
        }
    }
}
