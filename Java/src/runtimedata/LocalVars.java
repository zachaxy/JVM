package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:
 */
public class LocalVars {
    Slot[] localVars;

    public LocalVars(int maxLocals) {
        if (maxLocals>0) {
            localVars = new Slot[maxLocals];
        }else{
            throw new NullPointerException("maxLocals<0");
        }
    }

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

    void setLong(int index, long var) {
        //先存低32位
        localVars[index].num = (int) (var & 0xFFFFFFFF);
        //再存高32位
        localVars[index + 1].num = (int) (var >> 32);
    }

    long getLong(int index) {
        int low = localVars[index].num;
        int high = localVars[index + 1].num;
        return (high << 32) | (low);
    }

    void setDouble(int index, double var) {
        long bits = Double.doubleToLongBits(var);
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
