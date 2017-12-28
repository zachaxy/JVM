package instructions.references;

import instructions.base.ClassInitLogic;
import instructions.base.Index16Instruction;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zclass;
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

        //判断其Class是否已经加载过,如果还未加载,那么调用其类的<clinit>方法压栈
        if (!clazz.isInitStarted()) {
            //当前指令已经是在执行new了,但是类还没有加载,所以当前帧先回退,让类初始化的帧入栈,等类初始化完成后,重新执行new;
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }

        if(clazz.isInterface() || clazz.isAbstract()){
            throw  new InstantiationError(clazz.thisClassName + " can't new");
        }

        Zobject ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
