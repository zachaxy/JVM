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
    // 该Object不仅作为普通对象的一个存在,同时也作为每个Class结构对应的object,该Class对象需要的额外信息保存在extra中
    // 最简单的其可以用来记录类对象对应的Class结构体指针;（目前来看只有元类对象才会设置该 extra 字段）
    public Object extra;

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


    // reflection,只实现了 int 和 ref，double 和 float 没有实现；
    public Zobject getRefVar(String name, String descriptor) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getRef(field.slotId);
    }

    public void setRefVar(String name, String descriptor, Zobject ref) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setRef(field.slotId, ref);
    }

    public int getIntVar(String name, String descriptor) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getInt(field.slotId);
    }

    public void setIntVar(String name, String descriptor, int val) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setInt(field.slotId, val);
    }

    public long getLongVar(String name, String descriptor) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getLong(field.slotId);
    }

    public void setLongVar(String name, String descriptor, long val) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setLong(field.slotId, val);
    }

    public float getFloatVar(String name, String descriptor) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getFloat(field.slotId);
    }

    public void setFloatVar(String name, String descriptor, float val) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setFloat(field.slotId, val);
    }

    public double getDoubleVar(String name, String descriptor) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        return slots.getDouble(field.slotId);
    }

    public void setDoubleVar(String name, String descriptor, double val) {
        Zfield field = clazz.getField(name, descriptor, false);
        Slots slots = (Slots) data;
        slots.setDouble(field.slotId, val);
    }
}
