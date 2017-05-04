package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:
 */
public class OperandStack {

    int size;  //初始值为0,在运行中,代表当前栈顶的index,还未使用,可以直接用,用完记得size++;
    Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            slots = new Slot[maxStack];
        } else {
            throw new NullPointerException("maxStack<0");
        }
    }

    void pushInt(int val) {
        slots[size].num = val;
        size++;
    }

    int popInt() {
        size--;
        return slots[size].num;
    }

    void pushFloat(float val) {
        slots[size].num = Float.floatToIntBits(val);
        size++;
    }

    float popFloat() {
        size--;
        return Float.intBitsToFloat(slots[size].num);
    }

    void pushLong(long val) {
        //低位
        slots[size].num = (int) (val & 0xFFFFFFFF);
        size++;
        //高位
        slots[size].num = (int) (val >> 32);
        size++;
    }

    long popLong() {
        size -= 2;
        int low = slots[size].num;
        int high = slots[size + 1].num;
        return (high << 32) | (low);
    }

    void pushDouble(double val) {
        long bits = Double.doubleToLongBits(val);
        pushLong(bits);
    }

    double popDouble() {
        long bits = popLong();
        return Double.longBitsToDouble(bits);
    }

    void pushRef(Zobject ref) {
        slots[size].ref = ref;
        size++;
    }

    Zobject popRef() {
        size--;
        return slots[size].ref;
    }
}
