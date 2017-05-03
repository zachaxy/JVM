package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc: 常量池实际上也是一个表
 */
public class ConstantPool {
    ConstantInfo[] infos;  //保存类文件常量池中的所有常量,常量分为多种类型,基本类型都有对应的常量,以及字符串等;(简言之,这就是常量池的抽象)

    int constantPoolCount; //class文件中常量池中的常量数量

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
            if ((infos[i] instanceof ConstantLongInfo) || (infos[i] instanceof ConstantDoubleInfo)) {
                i++;
            }
        }

    }

    //按索引查找常量,如果么有的话,直接抛异常;
    ConstantInfo getConstantInfo(int index) {
        if (0 < index && index <= constantPoolCount) {
            ConstantInfo info = infos[index];
            if (info != null) {
                return info;
            }
        }
        throw new RuntimeException("Invalid constant pool index!");
    }

    //常量池查找字段或方法的名字和描述符
    String getName(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    //常量池查找字段或方法的描述符,描述符其实就是由其对应的类型名字对应而成;
    String getType(int index) {
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        return getUtf8(info.descriptorIndex);
    }

    String[] getNameAndType(int index) {
        String[] str = new String[2];
        ConstantNameAndTypeInfo info = (ConstantNameAndTypeInfo) getConstantInfo(index);
        str[0] = getUtf8(info.nameIndex);
        str[1] = getUtf8(info.descriptorIndex);
        return str;
    }

    String getClassName(int index) {
        ConstantClassInfo info = (ConstantClassInfo) getConstantInfo(index);
        return getUtf8(info.nameIndex);
    }

    String getUtf8(int index) {
        return ((ConstantUtf8Info) getConstantInfo(index)).val;
    }

    public int getConstantPoolCount() {
        return constantPoolCount;
    }
}
