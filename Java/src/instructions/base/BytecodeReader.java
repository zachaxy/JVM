package instructions.base;

import Utils.ByteUtils;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class BytecodeReader {
    private byte[] code;  //byte的范围四-128~127,和go中的byte:0~255不同,所以在取数据的时候需要注意;
    private int pc;

    /**
     * @param code
     * @param pc
     */
    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }

    public int getPc() {
        return pc;
    }

    public byte readInt8() {
        byte res = code[pc];
        pc++;
        return res;
    }

    public int readUint8() {
        int res = code[pc];
        res = (res + 256) % 256;
        pc++;
        return res;
    }


    public int readInt16() {
        return (short) readUint16();
    }

    public int readUint16() {
        int a1 = readUint8();
        int a2 = readUint8();
        return (a1 << 8 | a2);
    }

    public int readInt32() {
        byte[] data = new byte[4];
        data[0] = readInt8();
        data[1] = readInt8();
        data[2] = readInt8();
        data[3] = readInt8();

        return ByteUtils.byteToInt32(data);
    }

    public int[] readInt32s(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = readInt32();
        }
        return data;
    }


    //4k对齐,没有对齐的会有填充数据,这些数据要忽略掉;
    public void skipPadding() {
        while (pc % 4 != 0) {
            readInt8();
        }
    }
}
