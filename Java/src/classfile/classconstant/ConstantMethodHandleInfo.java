package classfile.classconstant;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantMethodHandleInfo extends ConstantInfo {
    // TODO: 2017/5/3 0003 关于byte上界,自行处理;
    byte referenceKind;
    int referenceIndex;

    public ConstantMethodHandleInfo(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        referenceKind = reader.readUint8();
        referenceIndex = reader.readUint16();
    }
}
