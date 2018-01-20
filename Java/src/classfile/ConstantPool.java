package classfile;

import classfile.classconstant.*;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc: 常量池实际上也是一个表,这里用数组来实现，所以常量池这个类中持有一个常量数据
 * 至于这个数组的每一项的初始化，则根据读到的字节的tag不同而创建不同的常量子类
 * 常量池有多中项目类型，这个根据读取到的tag的不同创建不同的常量类，
 */
public class ConstantPool {
    //保存类文件常量池中的所有常量,常量分为多种类型,基本类型都有对应的常量,以及字符串等;(简言之,这就是常量池的抽象)
    ConstantInfo[] infos;

    public ConstantInfo[] getInfos() {
        return infos;
    }

    //class文件中常量池中的常量数量,注意返回的这个数量是包含0的，但是0是空的；
    private int constantPoolCount;
    private int realConstantPoolCount;

    public ConstantPool(ClassReader reader) {
        /*读出常量池的大小;接下来根据这个大小,生成常量信息数组;
        注意:
        1. 表头给出的常量池大小比实际大1,所以这样的话,虽然可能生成了这么大的,但是0不使用,直接从1开始;
        2. 有效的常量池索引是1~n–1。0是无效索引，表示不指向任何常量
        3. CONSTANT_Long_info和 CONSTANT_Double_info各占两个位置。
           也就是说，如果常量池中存在这两种常量，实际的常量数量比n–1还要少，而且1~n–1的某些数也会变成无效索引。
        */
        constantPoolCount = reader.readUint16();
        infos = new ConstantInfo[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            infos[i] = ConstantInfo.readConstantInfo(reader, this);
            realConstantPoolCount++;
//            System.out.println(i+":"+infos[i].getClass());
            if ((infos[i] instanceof ConstantLongInfo) || (infos[i] instanceof ConstantDoubleInfo)) {
                i++;
            }
        }

    }

    //按索引查找常量,如果没有的话,直接抛异常;
    private ConstantInfo getConstantInfo(int index) {
        if (0 < index && index < constantPoolCount) {
            ConstantInfo info = infos[index];
            if (info != null) {
                return info;
            }
        }
        throw new NullPointerException("Invalid constant pool index!");
    }

    //常量池查找字段或方法的名字和描述符
    public String getName(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    //常量池查找字段或方法的描述符,描述符其实就是由其对应的类型名字对应而成;
    public String getType(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.descriptorIndex);
    }

    public String[] getNameAndType(int index) {
        String[] str = new String[2];
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        str[0] = getUtf8(info.nameIndex);
        str[1] = getUtf8(info.descriptorIndex);
        return str;
    }

    public String getClassName(int index) {
        ConstantClassInfo info = (ConstantClassInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    //只要调用这个方法，一定是想去读字符串常量了，所以拿到index所对应的常量后，直接强转为ConstantUtf8Info，然后获取其val值；
    public String getUtf8(int index) {
        return ((ConstantUtf8Info) getConstantInfo(index)).val;
    }

    //测试方法，正式版本祛除
    public int getConstantPoolCount() {
        return realConstantPoolCount;
    }
}


