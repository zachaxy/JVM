package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:栈帧,执行方法所需的局部变量表大小和操作数栈深度是由编译器预先计算好的，存储在class文件method_info结构的Code属性中
 * 详细实现参考 {@link classfile.CodeAttribute}
 */
public class Zframe {
    Zframe lower;       //当前帧的 前一帧的引用;相当于单向链表的前一个指针
    LocalVars localVars;    //局部变量表的引用;
    OperandStack operandStack;  //操作数栈的引用;
    Zthread thread;         //当前栈帧所在的线程;
    int nextPC;             //frame中并不改变PC的值,其PC值是由ByteReader读取字节码不断改变的

    // TODO: 2017/5/4 0004
    public Zframe(Zthread thread, int maxLocals, int maxStack) {
        this.thread = thread;
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public Zthread getThread() {
        return thread;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }
}
