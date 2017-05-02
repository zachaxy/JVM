package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:本身并不存放字符串数据,只存了常量池索引，这个索引指向一个CONSTANT_Utf8_info常量,所以在readInfo中首先读出索引,这里独到的是47
 * 然后在常量池中找到第47个索引,强转为ConstantUtf8Info,获取其val;
 */
public class ConstantStringInfo extends ConstantInfo {
    ConstantPool constantPool;
    int stringIndex;

    public ConstantStringInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    //读取常量池索引
    @Override
    void readInfo(ClassReader reader) {
        stringIndex = reader.readUint16();
    }

    String getString() {
        return constantPool.getUtf8(stringIndex);
    }
}
