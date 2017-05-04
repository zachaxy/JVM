package runtimedata;

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
        if (maxLocals>0) {
            localVars = new Slot[maxLocals];
        }else{
            throw new NullPointerException("maxLocals<0");
        }
    }

    //提供了对int,float,long,double,引用的存取,这里要注意的是long和double是占用8字节的,所以使用了局部变量表中的两个槽位分别存储前四字节和后四字节
    void setInt(int index, int val) {
        localVars[index].num = val;
    }

    int getInt(int index) {
        return localVars[index].num;
    }

    void setFloat(int index, float val) {
        localVars[index].num = Float.floatToIntBits(val);
    }

    float getFloat(int index) {
        return Float.intBitsToFloat(localVars[index].num);
    }

    void setLong(int index, long val) {
        //先存低32位
        localVars[index].num = (int) (val & 0xFFFFFFFF);
        //再存高32位
        localVars[index + 1].num = (int) (val >> 32);
    }

    long getLong(int index) {
        int low = localVars[index].num;
        int high = localVars[index + 1].num;
        return (high << 32) | (low);
    }

    void setDouble(int index, double val) {
        long bits = Double.doubleToLongBits(val);
        setLong(index, bits);
    }

    double getDouble(int index) {
        long bits = getLong(index);
        return Double.longBitsToDouble(bits);
    }

    void setRef(int index, Zobject ref) {
        localVars[index].ref = ref;
    }

    Zobject getRef(int index) {
        return localVars[index].ref;
    }

}
