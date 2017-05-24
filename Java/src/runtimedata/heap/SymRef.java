package runtimedata.heap;

/**
 * Author: zhangxin
 * Time: 2017/5/24 0024.
 * Desc:
 * 现在不需要这个类了，如果
 */
public class SymRef {
    ZconstantPool cp;   //存放符号引用所在的运行时常量池指针,可以通过符号引用访问到运行时常量池，进一步又可以访问到类数据
    String className;   //存放类的完全限定名
    Zclass clazz;       //缓存解析后的类结构体指针，这样类符号引用只需要解析一次就可以了，后续可以直 接使用缓存值


}
