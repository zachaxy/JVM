package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantNameAndTypeInfo extends ConstantInfo {
    int nameIndex;
    int descriptorIndex;

    @Override
    void readInfo(ClassReader reader) {
        nameIndex = reader.readUint16();
        descriptorIndex = reader.readUint16();
    }
}
