package runtimedata.heap;

/**
 * Author: zhangxin
 * Time: 2017/5/24 0024.
 * Desc: 保存符号引用的一些共有属性;
 * 这里一个重要的工作是将被引用的类的class加载进来;
 */
public class SymRef {
    RuntimeConstantPool runtimeConstantPool;   //存放符号引用所在的运行时常量池指针,可以通过符号引用访问到运行时常量池，进一步又可以访问到类数据
    String className;   //存放类的完全限定名
    Zclass clazz;       //上述运行时常量池的宿主类中的符号引用的真正类,在外面访问时，根据 clazz 是否为 null 来决定是否执行 loadClass

    public SymRef(RuntimeConstantPool runtimeConstantPool) {
        this.runtimeConstantPool = runtimeConstantPool;
    }

    //类引用转直接引用
    public Zclass resolvedClass() {
        if (clazz == null) {
            resolvedClassRef();
        }
        return clazz;
    }

    //    当前类(cp的宿主类)d中,如果引用了类c,那么就将c加载进来
    private void resolvedClassRef() {
        Zclass d = runtimeConstantPool.clazz;
        Zclass c = d.loader.loadClass(className);
        //在这里判断下 d 能否访问 c
        if (!c.isAccessibleTo(d)) {
            throw new IllegalAccessError(d.thisClassName + " can't access " + c.thisClassName);
        }
        clazz = c;
    }

}
