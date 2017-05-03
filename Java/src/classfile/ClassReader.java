package classfile;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ClassReader {

    byte[] data;
    int index = 0;

    public ClassReader(byte[] data) {
        this.data = data;
    }

    // u1
    public byte readUint8() {
        byte res = data[index++];
        return res;
    }


    // u2 这里是读取一个无符号的16位整,java中没有,只能用int来代替吧;
    public int readUint16() {
        byte[] res = new byte[2];
        res[0] = data[index++];
        res[1] = data[index++];
        return ByteUtils.bytesToU16(res);
    }

    // u4
    public byte[] readUint32() {
        byte[] res = new byte[4];
        res[0] = data[index++];
        res[1] = data[index++];
        res[2] = data[index++];
        res[3] = data[index++];
//        return ByteUtils.bytesToU32(res);  //如果需要转换的话,自行调用ByteUtils中的方法;
        return res;
    }

    public byte[] readUint64() {
        byte[] res = new byte[8];
        res[0] = data[index++];
        res[1] = data[index++];
        res[2] = data[index++];
        res[3] = data[index++];
        res[4] = data[index++];
        res[5] = data[index++];
        res[6] = data[index++];
        res[7] = data[index++];
        return res;
    }

    public int[] readUint16s() {
        int n = readUint16();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = readUint16();
        }
        return data;
    }

    public byte[] readBytes(int n) {
        byte[] res = new byte[n];
        for (int i = 0; i < n; i++) {
            res[i] = data[index++];
        }
        return res;
    }


}
