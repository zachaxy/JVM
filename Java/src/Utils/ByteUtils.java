package Utils;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ByteUtils {


    public static String bytesToHexString(byte[] src) {
        return bytesToHexString(src, src.length);
    }

    /**
     * @param src 待转换的字节数组
     * @param len 只转换字节数组中的前len个字节
     * @return 转换成的字符串, 考虑到这是对底层数据的操作,
     */
    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || len <= 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //Java中并没有u16,所以这里使用int来表示;
    public static int bytesToU16(byte[] data) {
        assert data.length == 2;
        return (data[0] + 256) % 256 * 256 + (data[1] + 256) % 256;
    }

    //这个方法和下个方法区别在哪里,返回值不同啊...
    /*public static long bytesToU32(byte[] data) {
        assert data.length == 4;
        long res = 0;
        for (int i = 0; i < 4; i++) {
            res += res * 256 + (data[i] + 256) % 256;
        }
        return res;
    }*/

    public static int byteToInt32(byte[] data) {
        assert data.length == 4;
        int res = 0;
        for (int i = 0; i < data.length; i++) {
            res = res << 8 | (data[i] + 256) % 256;
        }
        return res;
    }

    public static long byteToLong64(byte[] data) {
        assert data.length == 8;
        long res = 0;
        for (int i = 0; i < data.length; i++) {
            res = res << 8 | (data[i] + 256) % 256;
        }
        return res;
    }


    public static float byte2Float32(byte[] b) {
        int i = byteToInt32(b);
        return Float.intBitsToFloat(i);
    }


    public static double byte2Double64(byte[] b) {
        long l = byteToLong64(b);
        return Double.longBitsToDouble(l);
    }
}
