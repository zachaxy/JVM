package runtimedata;

import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:操作数栈,底层其实还是用数组来实现的,但是对外要暴露的接口是栈的特性
 * 包含的操作和局部变量表类似
 * 操作数栈的大小是编译期已经确定的，保存在code属性中，所以可以用Slot数组实现
 * 但要和 LocalVars 区分开，本地变量表按索引访问，操作数栈是用数组模拟的栈；方法栈是用单向链表模拟的栈
 * <p>
 * fixBug:统一说明：在操作数栈中，涉及到引用的操作：popRef 和 popSlot
 * 之前的做法是将 ref pop 出来之后，将数组中当前位置设置为 null，
 * 但是存在的问题是：操作数栈中的如果有多个的引用，都指向相同的一个实例对象，
 * 将其中的一个引用设置为 null，相当于把对象在堆中的空间设置为 null 了，
 * 这回导致操作数栈中所有的引用都会变成 null，后续会产生 NullPointerException
 * 所以这里在弹栈之后，不在设置为 null；
 * 操作数栈本身是不断复用的，故不考虑 GC 问题
 */
public class OperandStack {

    //初始值为0,在运行中,代表当前栈顶的index,还未使用,可以直接用,用完记得size++;
    private int size;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if (maxStack >= 0) {
            slots = new Slot[maxStack];
        } else {
            throw new NullPointerException("maxStack<0");
        }
    }

    public void pushBoolean(boolean val) {
        if (val) {
            pushInt(1);
        } else {
            pushInt(0);
        }
    }

    public boolean popBoolean() {
        return popInt() == 1;
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
        return slots[size].ref;
    }

    public void pushSlot(Slot slot) {
        slots[size] = slot;
        size++;
    }

    public Slot popSlot() {
        size--;
        return slots[size];
    }

    //新添加的方法,根据参数n,返回操作数栈中的倒数第n个引用;
    public Zobject getRefFromTop(int n) {
        return slots[size - 1 - n].ref;
    }

    //just for test!
    public boolean isEmpty() {
        return size == 0;
    }

    //清空操作数栈，直接将 size 设置为0，而不是将所有的 slot 都设为 null；因为这样可能会引起其它问题
    public void clear() {
        size = 0;
    }
}

