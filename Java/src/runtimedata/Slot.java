package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc:局部变量表的数据,根据Java虚拟机规范，这个数组的每个元素至少可以容纳一个int或引用值，两个连续的元素可以容纳一个long或double值。
 * 可问题是用什么数据结构来存储,目前并没有很好的方法,只能将这个类设计为既包含一个int有包含一个引用,但是在真正使用的时候,二者只会用其一
 * 导致了一半内存的浪费;
 */
public class Slot {
    int num;
    Zobject ref;

    public Slot(int num, Zobject ref) {
        this.num = num;
        this.ref = ref;
    }
}
