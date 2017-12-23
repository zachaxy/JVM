package classfile.classconstant;

import Utils.ByteUtils;
import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantDoubleInfo extends ConstantInfo {
    double val;

    public ConstantDoubleInfo(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        byte[] data = reader.readUint64();
        val = ByteUtils.byte2Double64(data);
    }

    public double getVal() {
        return val;
    }
}
