package classfile.classconstant;

import Utils.ByteUtils;
import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantFloatInfo extends ConstantInfo {
    float val;

    public ConstantFloatInfo(int i) {
        type = i;
    }

    @Override
    void readInfo(ClassReader reader) {
        byte[] data = reader.readUint32();
        val = ByteUtils.byte2Float32(data);
    }


    public float getVal() {
        return val;
    }
}
