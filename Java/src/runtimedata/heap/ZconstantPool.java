package runtimedata.heap;

import classfile.ConstantInfo;
import classfile.ConstantPool;

import java.util.NoSuchElementException;

/**
 * Author: zhangxin
 * Time: 2017/5/20 0020.
 * Desc: 运行时常量池,注意和字节码中的常量池做区分，这里指的是线程共享区的常量；
 * 实现的功能是：把class文件中的常量池转换成运行时常量池
 * 既然内部保存的内容是一样的，何不直接拿过来使用，不需要再拷贝一份了；
 * 原本的做法是只讲常量池中的value拷贝过来;
 * 对于直接字面面可以直接使用，但是对于引用类型如何处理？？？
 */
public class ZconstantPool {

    Zclass clazz;
    ConstantInfo[] infos;

//    主要作用是将class文件中的常量池转换为运行时常量池;这里没有做转换,而是直接拿过来用;
    public ZconstantPool(Zclass clazz, ConstantPool cfcp) {
        this.clazz = clazz;
        this.infos = cfcp.getInfos();
        /*for (int i = 1; i < cpCount; i++) {
            ConstantInfo info = infos[i];
            switch (info.getType()){

            }
        }*/
    }

    //这里只是把ConstantInfo返回，至于具体是那种数据，可以根据其中保存的type字段来判断，并拿到对应类型的值；
    public ConstantInfo getConstant(int index) {
        ConstantInfo info = infos[index];
        if (info != null) {
            return info;
        }

        throw new NoSuchElementException("No constants at index " + index);
    }
}
