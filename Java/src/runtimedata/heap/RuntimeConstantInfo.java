package runtimedata.heap;

/**
 * @author zachaxy
 * @date 17/12/25
 * desc:运行时常量，区别于 class 文件中的常量
 * 本想用泛型来约束运行时常量池的类型，这样在取运行时常量池的值时可以避免丑陋的类型强壮代码风格
 * 无奈的是，运行时常量池是放在数组中的，一旦放入数组，其对象的泛型类型也就消失了
 * 所以使用标志位 type 来识别类型，取出后再进行类型转换
 */
public class RuntimeConstantInfo<T> {
    private T value;
    private int type;

    RuntimeConstantInfo(T value,int type){
        this.value = value;
        this.type = type;
    }

    public T getValue(){
        return value;
    }

    public int getType() {
        return type;
    }
}
