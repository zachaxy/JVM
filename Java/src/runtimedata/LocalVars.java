package runtimedata;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 局部变量表是按索引访问的，所以很自然，可以把它想象成一个数组。
 * 根据Java虚拟机规范，这个数组的每个元素至少可以容纳一个int或引用值，两个连续的元素可以容纳一个long或double值。
 * 注:这里并没有真的对boolean、byte、short和char类型定义存取方法，因为这些类型的值都是转换成int值类来处理的(4K对齐)
 */
public class LocalVars extends Slots {
    public LocalVars(int size) {
        super(size);
    }
}
