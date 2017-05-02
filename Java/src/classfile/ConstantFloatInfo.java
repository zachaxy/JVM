package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantFloatInfo extends ConstantInfo {
    float val;
    @Override
    void readInfo(ClassReader reader) {
        byte[] data = reader.readUint32();
        val = ByteUtils.byte2Float32(data);
    }

    public float getVal() {
        return val;
    }
}
