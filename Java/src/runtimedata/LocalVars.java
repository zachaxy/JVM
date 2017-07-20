package runtimedata;

import runtimedata.heap.Zobject;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 局部变量表是按索引访问的，所以很自然，可以把它想象成一个数组。
 * 根据Java虚拟机规范，这个数组的每个元素至少可以容纳一个int或引用值，两个连续的元素可以容纳一个long或double值。
 * 注:这里并没有真的对boolean、byte、short和char类型定义存取方法，因为这些类型的值都是转换成int值类来处理的(4K对齐)
 */
public class LocalVars {
    private Slot[] localVars; //局部变量表,JVM规定其按照索引访问,所以将其设置为数组

    public LocalVars(int maxLocals) {
        if (maxLocals > 0) {
            localVars = new Slot[maxLocals];
        } else {
            throw new NullPointerException("maxLocals<0");
        }
    }

    //提供了对int,float,long,double,引用的存取,这里要注意的是long和double是占用8字节的,所以使用了局部变量表中的两个槽位分别存储前四字节和后四字节
    public void setInt(int index, int val) {
        Slot slot = new Slot();
        slot.num = val;
        localVars[index] = slot;
    }

    public int getInt(int index) {
        return localVars[index].num;
    }

    public void setFloat(int index, float val) {
        Slot slot = new Slot();
        slot.num = Float.floatToIntBits(val);
        localVars[index] = slot;
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(localVars[index].num);
    }

    public void setLong(int index, long val) {
        //先存低32位
        Slot slot1 = new Slot();
        slot1.num = (int) (val);
        localVars[index] = slot1;
        //再存高32位
        Slot slot2 = new Slot();
        slot2.num = (int) (val >> 32);
        localVars[index + 1] = slot2;
    }

    public long getLong(int index) {
        int low = localVars[index].num;
        long high = localVars[index + 1].num;
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
        Slot slot = new Slot();
        slot.ref = ref;
        localVars[index] = slot;
    }

    public Zobject getRef(int index) {
        return localVars[index].ref;
    }

    public static void main(String[] args) {
        LocalVars localVars = new LocalVars(10);
        localVars.setInt(0,100);
        localVars.setInt(1,-100);
        localVars.setLong(2,2997934580L);
        localVars.setLong(4,-2997934580L);
        localVars.setFloat(6,3.1415926f);
        localVars.setDouble(7,2.141592678912);
        System.out.println(localVars.getInt(0));
        System.out.println(localVars.getInt(1));
        System.out.println(localVars.getLong(2));
        System.out.println(localVars.getLong(4));
        System.out.println(localVars.getFloat(6));
        System.out.println(localVars.getDouble(7));

    }
}
