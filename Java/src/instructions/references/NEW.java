package instructions.references;

import instructions.base.Index16Instruction;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.Zclass;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/7/24.
 * Desc: uint16的索引,来自字节码,通过该索引,从当前类的运行时常量池中找到类符号引用;
 * 解析该类符号引用,可以拿到类数据,然后创建对象,并把对象引用压入操作数栈;
 */
public class NEW extends Index16Instruction {

    @Override
    public void execute(Zframe frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        ClassRef classRef = (ClassRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        Zclass clazz = classRef.resolvedClass();
        // TODO:class的初始化未实现
        if(clazz.isInterface() || clazz.isAbstract()){
            throw  new InstantiationError(clazz.thisClassName + " can't new");
        }

        Zobject ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
