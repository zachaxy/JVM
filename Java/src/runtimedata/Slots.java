package runtimedata;

import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/7/22.
 * Desc:
 */
public class Slots {
    private Slot[] slots;

    public Slots(int size) {
        slots = new Slot[size];
        for (int i = 0; i < size; i++) {
            slots[i] = new Slot();
        }
    }

    //提供了对int,float,long,double,引用的存取,这里要注意的是long和double是占用8字节的,所以使用了局部变量表中的两个槽位分别存储前四字节和后四字节
    public void setInt(int index, int val) {
//        Slot slot = new Slot();
//        slot.num = val;
        slots[index].num = val;
    }

    public int getInt(int index) {
        return slots[index].num;
    }

    public void setFloat(int index, float val) {
//        Slot slot = new Slot();
//        slot.num = Float.floatToIntBits(val);
        slots[index].num = Float.floatToIntBits(val);
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(slots[index].num);
    }

    public void setLong(int index, long val) {
        //先存低32位
//        Slot slot1 = new Slot();
//        slot1.num = (int) (val);
        slots[index].num = (int) (val);
        //再存高32位
//        Slot slot2 = new Slot();
//        slot2.num = (int) (val >> 32);
        slots[index + 1].num = (int) (val >> 32);
    }

    public long getLong(int index) {
        int low = slots[index].num;
        long high = slots[index + 1].num;
        return ((high & 0x000000ffffffffL) << 32) | (low & 0x00000000ffffffffL);
    }

    public void setDouble(int index, double val) {
        long bits = Double.doubleToLongBits(val);
        setLong(index, bits);
    }

    public double getDouble(int index) {
        long bits = getLong(index);
        return Double.longBitsToDouble(bits);
    }

    public void setRef(int index, Zobject ref) {
//        Slot slot = new Slot();
//        slot.ref = ref;
        slots[index].ref = ref;
    }

    public Zobject getRef(int index) {
        return slots[index].ref;
    }
}
