package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zclass;
import runtimedata.heap.Zobject;

/**
 * @author zachaxy
 * @date 17/12/29
 * desc:创建引用类型数组,注意这里是一维的!!!
 * 如果创建的是 String[],那么从运行时常量池拿到 String 的符号引用，通过符号引用将 String 类加载进来
 * 接下来再将 String[] 类加载进来！
 * 最后通过 String[] 类创建 字符串数组对象，并压入操作数栈
 */
public class ANEW_ARRAY extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        //首先获取到该数组的引用类型;eg:String [] arr = new String[2];  那么获取到的类是:java.lang.String
        ClassRef classRef = (ClassRef) runtimeConstantPool.getRuntimeConstant(this.index).getValue();

        //该引用类型若没加载过,那么先将该引用类型加载进来;
        Zclass componentClass = classRef.resolvedClass();

        OperandStack operandStack = frame.getOperandStack();
        int count = operandStack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("" + count);
        }
        //根据基础类型:java/lang/String,得到数组类[Ljava/lang/String;
        Zclass arrClazz = componentClass.arrayClass();
        //根据数组类去创建具体的数组对象;
        Zobject arr = arrClazz.newArray(count);
        operandStack.pushRef(arr);
    }
}
