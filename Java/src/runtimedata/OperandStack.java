package runtimedata;

import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:操作数栈,底层其实还是用数组来实现的,但是对外要暴露的接口是栈的特性
 * 包含的操作和局部变量表类似
 * 操作数栈的大小是编译期已经确定的，保存在code属性中，所以可以用Slot数组实现
 * 但要和 LocalVars 区分开，本地变量表按索引访问，操作数栈是用数组模拟的栈；方法栈是用单向链表模拟的栈
 */
public class OperandStack {

    //初始值为0,在运行中,代表当前栈顶的index,还未使用,可以直接用,用完记得size++;
    private int size;
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
        //下面的low在和后面的数进行&运算时自动转换为long;
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
