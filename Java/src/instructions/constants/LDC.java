package instructions.constants;

import classfile.classconstant.ConstantInfo;
import instructions.base.Index8Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.RuntimeConstantInfo;
import runtimedata.heap.Zclass;

/**
 * @author zachaxy
 * @date 17/12/26
 * desc:获取操作数index，通过 index 来获取运行时常量池中的常量
 */
public class LDC extends Index8Instruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack operandStack = frame.getOperandStack();
        Zclass clazz = frame.getMethod().getClazz();
        RuntimeConstantInfo runtimeConstant = clazz.getRuntimeConstantPool().getRuntimeConstant(index);
        switch (runtimeConstant.getType()) {
            case ConstantInfo.CONSTANT_Integer:
                operandStack.pushInt((Integer) runtimeConstant.getValue());
                break;
            case ConstantInfo.CONSTANT_Float:
                operandStack.pushFloat((Float) runtimeConstant.getValue());
                break;
//            case ConstantInfo.CONSTANT_String:
//                break;
//            case ConstantInfo.CONSTANT_Class:
//                break;
            // case MethodType, MethodHandle //Java7中的特性，不在本虚拟机范围内
            default:
                break;
        }
    }
}
