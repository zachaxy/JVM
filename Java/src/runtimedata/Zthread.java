package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 定义Thread结构体,目前只定义了pc和stack两个字段,每个线程中都持有一个虚拟机栈的引用
 * Java虚拟机规范对Java虚拟机栈的约束也相当宽松,
 * 虚拟机栈可以是连续的空间，也可以不连续
 * 可以是固定大小，也可以在运行时动态扩展
 * <p>
 * 如果Java虚拟机栈有大小限制，且执行线程所需的栈空间超出了这个限制，会导致StackOverflowError异常抛出。
 * 如果Java虚拟机栈可以动态扩展，但是内存已经耗尽，会导致OutOfMemoryError异常抛出。
 * <p>
 * 其实Java命令提供了-Xss选项来设置Java虚拟机栈大小
 */
public class Zthread {
    int pc;
    Zstack stack; //Stack结构体（Java虚拟机栈）的引用;

    public Zthread() {
        //默认栈的大小是1024,也就是说可以存放1024个栈帧
        stack = new Zstack(1024);
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void pushFrame(Zframe frame) {
        stack.push(frame);
    }

    public Zframe popFrame() {
        return stack.pop();
    }

    public Zframe getCurrentFrame() {
        return stack.top();
    }

    public Zframe createFrame(int maxLocals, int maxStack) {
        return new Zframe(this, maxLocals, maxStack);
    }
}
