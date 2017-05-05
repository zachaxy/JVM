package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:操作数栈;
 * 包含的操作和局部变量表类似
 * 操作数栈的大小是编译器已经确定的，所以可以用[]Slot实现
 */
public class OperandStack {

    private int size;  //初始值为0,在运行中,代表当前栈顶的index,还未使用,可以直接用,用完记得size++;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack > 0) {
            slots = new Slot[maxStack];
        } else {
            throw new NullPointerException("maxStack<0");
        }
    }

    public void pushInt(int val) {
        Slot slot = new Slot();
        slot.num = val;
        slots[size] = slot;
        size++;
    }

    public int popInt() {
        size--;
        return slots[size].num;
    }

    public void pushFloat(float val) {
        Slot slot = new Slot();
        slot.num = Float.floatToIntBits(val);
        slots[size] = slot;
        size++;
    }

    public float popFloat() {
        size--;
        return Float.intBitsToFloat(slots[size].num);
    }

    public void pushLong(long val) {
        //低位
        Slot slot1 = new Slot();
        slot1.num = (int) (val);
        slots[size] = slot1;
        size++;
        //高位
        Slot slot2 = new Slot();
        slot2.num = (int) (val >> 32);
        slots[size] = slot2;
        size++;
    }

    public long popLong() {
        size -= 2;
        int low = slots[size].num;
        long high = slots[size + 1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public void pushDouble(double val) {
        long bits = Double.doubleToLongBits(val);
        pushLong(bits);
    }

    public double popDouble() {
        long bits = popLong();
        return Double.longBitsToDouble(bits);
    }

    public void pushRef(Zobject ref) {
        Slot slot = new Slot();
        slot.ref = ref;
        slots[size] = slot;
        size++;
    }

    public Zobject popRef() {
        size--;
        Zobject ref = slots[size].ref;
        slots[size].ref = null; //避免内存泄露;
        return ref;
    }

    public void pushSlot(Slot slot) {
        slots[size] = slot;
        size++;
    }

    public Slot popSlot() {
        size--;
        Slot slot = slots[size];
        slots[size] = null; //释放内存,是GC可以回收该slot;
        return slot;
    }
}
