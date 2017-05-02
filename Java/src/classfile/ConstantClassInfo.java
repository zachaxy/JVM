package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:表示类或者接口的符号引用
 */
public class ConstantClassInfo extends ConstantInfo {
    ConstantPool constantPool;
    int nameIndex;

    public ConstantClassInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    void readInfo(ClassReader reader) {
        nameIndex = reader.readUint16();
    }

    String getName(){
        return constantPool.getUtf8(nameIndex);
    }
}
