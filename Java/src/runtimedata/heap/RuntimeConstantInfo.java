package runtimedata.heap;

/**
 * @author zachaxy
 * @date 17/12/25
 * desc:运行时常量，区别于 class 文件中的常量
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
