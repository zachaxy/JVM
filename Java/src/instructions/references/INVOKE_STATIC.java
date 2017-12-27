package instructions.references;

import instructions.base.Index16Instruction;
import instructions.base.MethodInvokeLogic;
import runtimedata.Zframe;
import runtimedata.heap.MethodRef;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zmethod;

/**
 * @author zachaxy
 * @date 17/12/27
 * desc:静态方法调用指令
 * 静态方法,在调用阶段即可确定是哪个方法
 */
public class INVOKE_STATIC extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        //通过index,拿到方法符号引用
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        Zmethod resolvedMethod = methodRef.resolvedMethod();
        if (!resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        //TODO:类初始化待检测

        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
