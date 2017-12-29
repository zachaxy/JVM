package runtimedata.heap;

import runtimedata.Slots;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 用来模拟Java中的Object类,这里只是简单的模拟定义一个类,用来盛放索引的
 */
public class Zobject {
    //存放一个class的成员,用来调用类的方法,静态成员变量
    private Zclass clazz;
    //存放的是非静态成员变量,包含父类+ 自己的；或者存放数组
    private Object data;
    private Object extra;

    public Zobject(Zclass clazz) {
        this.clazz = clazz;
        data = new Slots(clazz.instanceSlotCount);
    }

    public Zobject(Zclass clazz, Object data, Object extra) {
        this.clazz = clazz;
        this.data = data;
        this.extra = extra;
    }

    public Slots getFields() {
        return (Slots) data;
    }

    public Zclass getClazz() {
        return clazz;
    }

    // 判断当前类是都是否是 target 的子类
    public boolean isInstanceOf(Zclass target) {
        return target.isAssignableFrom(this.clazz);
    }

    //为数组添加一些特有的方法：
    public byte[] getBytes() {
        return (byte[]) data;
    }

    public char[] getChars() {
        return (char[]) data;
    }

    public short[] getShorts() {
        return (short[]) data;
    }

    public int[] getInts() {
        return (int[]) data;
    }

    public long[] getLongs() {
        return (long[]) data;
    }

    public float[] getFloats() {
        return (float[]) data;
    }

    public double[] getDoubles() {
        return (double[]) data;
    }

    public Zobject[] getRefs() {
        return (Zobject[]) data;
    }

    public int getArrayLen() {
        switch (data.getClass().getSimpleName()) {
            case "byte[]":
                return getBytes().length;
            case "short[]":
                return getShorts().length;
            case "char[]":
                return getChars().length;
            case "int[]":
                return getInts().length;
            case "long[]":
                return getLongs().length;
            case "float[]":
                return getFloats().length;
            case "double[]":
                return getDoubles().length;
            case "Zobject[]":
                return getRefs().length;
            default:
                throw new RuntimeException("called length on a none array object!");
        }
    }
}
