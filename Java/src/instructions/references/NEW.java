package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZconstantPool;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/7/24.
 * Desc: uint16的索引,来自字节码,通过该索引,从当前类的运行时常量池中找到类符号引用;
 * 解析该类符号引用,可以拿到类数据,然后创建对象,并把对象引用推入栈顶;
 */
public class NEW extends Index16Instruction {

    @Override
    public void execute(Zframe frame) {
        ZconstantPool cp = frame.getMethod().getClassMember().getClazz().getConstantPool();
        // TODO: 2017/7/26 常量池的转换尚未实现;
        ClassRef classRef = null;//cp.getConstant(index);
        Zclass clazz = classRef.resolvedClass();

        if(clazz.isInterface() || clazz.isAbstract()){
            throw  new RuntimeException("java.lang.InstantiationError");
        }

        Zobject ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
