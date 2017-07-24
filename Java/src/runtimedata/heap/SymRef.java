package runtimedata.heap;

/**
 * Author: zhangxin
 * Time: 2017/5/24 0024.
 * Desc: 保存符号引用的一些共有属性;
 * 这里一个重要的工作是将被引用的类的class加载进来;
 * 如何理解这里的引用?假设当前类是D,D中需要一些其它的类,这里其它的类就是引用;如果当前正在加载D,引用了C
 * 那么也需要将C加载进来;
 */
public class SymRef {
    ZconstantPool cp;   //存放符号引用所在的运行时常量池指针,可以通过符号引用访问到运行时常量池，进一步又可以访问到类数据
    String className;   //存放类的完全限定名
    Zclass clazz;       //缓存解析后的类结构体指针，这样类符号引用只需要解析一次就可以了，后续可以直 接使用缓存值

    public Zclass resolvedClass(){
        if (clazz == null){
            resolvedClassRef();
        }
        return clazz;
    }

//    当前类d中,如果引用了类c,那么就将c加载进来
    private void resolvedClassRef(){
        Zclass d = cp.clazz;
        Zclass c = d.loader.loadClass(className);
        //在这里判断下 d 能否访问 c
        if (!c.isAccessibleTo(d)){
             throw new RuntimeException("");
        }
        clazz = c;
    }

}
