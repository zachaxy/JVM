package instructions.references;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.Zthread;
import runtimedata.heap.Zobject;
import znative.java.lang.NStackTraceElement;

/**
 * @author zachaxy
 * @date 18/1/2
 */
public class ATHROW extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        //异常引用对象;throw new Exception();
        Zobject ex = frame.getOperandStack().popRef();
        if (ex == null) {
            throw new NullPointerException();
        }

        //获取当前thread,是想从thread中获取所有的frame(一个frame就是一个方法调用过程的抽象)
        Zthread thread = frame.getThread();
        if (!findAndGotoExceptionHandler(thread, ex)) {
            //所有的方法调用栈均不能处理该异常了,那么交由JVM来处理;
            handleUncaughtException(thread, ex);
        }
    }


    //从当前帧开始,遍历Java虚拟机栈,查找方法的异常处理表
    private boolean findAndGotoExceptionHandler(Zthread thread, Zobject ex) {
        while (true) {
            Zframe frame = thread.getCurrentFrame();
            //正常情况下,获取一条指令后,bytereader中的pc是要+1,指向下一条指令的地址
            // 但是athrow指令比较特殊,因为现在已经抛出异常了,不能继续向下执行了,所以要回退;
            int pc = frame.getNextPC() - 1;

            //此时pc指向的是throw异常的那一行;因为接下来就要在当前方法的异常处理表中寻找可以处理当前pc指向的指令所抛出的一场了
            int handlerPC = frame.getMethod().findExceptionHandler(ex.getClazz(), pc);

            //当前方法能处理自己抛出的异常
            if (handlerPC > 0) {
                OperandStack operandStack = frame.getOperandStack();
                operandStack.clear();//清空操作数栈
                operandStack.pushRef(ex);//将抛出的异常放进去
                frame.setNextPC(handlerPC);//指令跳转到对应的catch块中
                return true;
            }

            //能走到这一步,说明当前方法不能处理当前方法抛出的异常,需要回到调用该方法的帧frame
            thread.popFrame();

            //整个调用栈都无法处理异常，交由 JVM 来处理吧；JVM 处理的方法就是下面的 handleUncaughtException
            if (thread.isStackEmpty()) {
                break;
            }
        }
        return false;
    }


    //打印异常信息,包括调用栈的链;同时,此时虚拟机栈都已经清空了,所以整个JVM也就终止了;
    private void handleUncaughtException(Zthread thread, Zobject ex) {
        //其实能执行到这一步表明方法栈已经被清空了;
        thread.clearStack();
        NStackTraceElement[] stet = (NStackTraceElement[]) ex.extra;
        for (int i = 0; i < stet.length; i++) {
            System.out.println(stet[i]);
        }
    }
}
