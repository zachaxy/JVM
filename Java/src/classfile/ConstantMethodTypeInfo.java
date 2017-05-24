package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantMethodTypeInfo extends ConstantInfo {
    // TODO: 2017/5/3 0003 关于byte上界,自行处理;

    int descriptorIndex;

    public ConstantMethodTypeInfo(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        descriptorIndex = reader.readUint16();
    }
}
