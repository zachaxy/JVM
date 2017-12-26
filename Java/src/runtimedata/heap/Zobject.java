package runtimedata.heap;

import runtimedata.Slots;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 用来模拟Java中的Object类,这里只是简单的模拟定义一个类,用来盛放索引的
 */
public class Zobject {
    //存放一个class的成员,用来调用类的方法,静态成员变量
    Zclass clazz;
    //存放的是非静态成员变量,包含父类+ 自己的
    Slots fields;

    public Zobject(Zclass clazz) {
        this.clazz = clazz;
        fields = new Slots(clazz.instanceSlotCount);
    }

    public Slots getFields() {
        return fields;
    }

    public Zclass getClazz() {
        return clazz;
    }

    // 判断当前类是都是否是 target 的子类
    public boolean isInstanceOf(Zclass target) {
        return target.isAssignableFrom(this.clazz);
    }
}
