package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc: 使用4字节存储整数常量
 */
public class ConstantIntegerInfo extends ConstantInfo {
    int val;

    @Override
    void readInfo(ClassReader reader) {
        byte[] data = reader.readUint32();
        val = ByteUtils.byteToInt32(data);
    }

    public int getVal() {
        return val;
    }
}
