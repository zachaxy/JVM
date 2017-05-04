package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:
 */
public class Zthread {
    int pc;
    Zstack stack;

    public Zthread() {
        stack = new Zstack(1024);
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    void pushFrame(Zframe frame) {
        stack.push(frame);
    }

    Zframe popFrame() {
        return stack.pop();
    }

    public Zframe getCurrentFrame() {
        return stack.top();
    }
}
