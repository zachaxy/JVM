package classfile.classconstant;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc: ava7 中的属性，在本 JVM 中未实现
 */
public class ConstantMethodHandleInfo extends ConstantInfo {
    //关于byte上界,自行处理;
    private byte referenceKind;
    private int referenceIndex;

    public ConstantMethodHandleInfo(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        referenceKind = reader.readUint8();
        referenceIndex = reader.readUint16();
    }

    public int getReferenceKind() {
        return (referenceKind + 256) % 256;
    }

    public int getReferenceIndex() {
        return referenceIndex;
    }
}
