package classfile.classconstant;

import classfile.ClassReader;

import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:放的是MUTF-8编码的字符串,
 * 注意，字符串在class文件中是以MUTF-8（Modified UTF-8）方式编码的。
 * <p>
 * MUTF-8编码方式和UTF-8大致相同，但并不兼容。
 * 差别有两点：
 * 一是null字符（代码点U+0000）会被编码成2字节：0xC0、0x80；
 * 二是补充字符（Supplementary Characters，代码点大于U+FFFF的Unicode字符）是按UTF-16拆分为代理对（Surrogate Pair）分别编码的
 * <p>
 * 字段名(变量名)、字段描述符等就是以字符串的形式存储在class文件中的
 */
public class ConstantUtf8Info extends ConstantInfo {
    public String val;

    public ConstantUtf8Info(int i) {
        type = i;
    }


    @Override
    void readInfo(ClassReader reader) {
        int len = reader.readUint16();
        byte[] data = reader.readBytes(len);
        try {
            val = decodeMUTF8(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将MUTF8转为UTF8编码, 根据java.io.DataInputStream.readUTF（）方法改写。
    private static String decodeMUTF8(byte[] bytearr) throws IOException {
        int utflen = bytearr.length;
        char[] chararr = new char[utflen];
        int c, char2, char3;
        int count = 0;
        int chararr_count = 0;

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            if (c > 127) break;
            count++;
            chararr[chararr_count++] = (char) c;
        }

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    /* 0xxxxxxx*/
                    count++;
                    chararr[chararr_count++] = (char) c;
                    break;
                case 12:
                case 13:
                    /* 110x xxxx   10xx xxxx*/
                    count += 2;
                    if (count > utflen)
                        throw new UTFDataFormatException("malformed input: partial character at end");
                    char2 = (int) bytearr[count - 1];
                    if ((char2 & 0xC0) != 0x80)
                        throw new UTFDataFormatException("malformed input around byte " + count);
                    chararr[chararr_count++] = (char) (((c & 0x1F) << 6) |
                            (char2 & 0x3F));
                    break;
                case 14:
                    /* 1110 xxxx  10xx xxxx  10xx xxxx */
                    count += 3;
                    if (count > utflen)
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    char2 = (int) bytearr[count - 2];
                    char3 = (int) bytearr[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                        throw new UTFDataFormatException(
                                "malformed input around byte " + (count - 1));
                    chararr[chararr_count++] = (char) (((c & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F) << 0));
                    break;
                default:
                    /* 10xx xxxx,  1111 xxxx */
                    throw new UTFDataFormatException(
                            "malformed input around byte " + count);
            }
        }
        // The number of chars produced may be less than utflen
        return new String(chararr, 0, chararr_count);
    }
    /*private static String decodeMUTF8(byte[] bytearr) {
        int utfLen = bytearr.length;
        char[] chararr = new char[utfLen];
        char c, char2, char3;
        int count = 0;
        int chararr_count = 0;

        while (count < utfLen) {
            c = (char) ((bytearr[count] + 256) % 256);
            if (c > 127) {
                break;
            }
            count++;
            chararr[chararr_count] = c;
            chararr_count++;
        }

        while (count < utfLen) {
            c = (char) ((bytearr[count] + 256) % 256);
            switch (c >> 4) {
                *//* 0xxxxxxx*//*
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    count++;
                    chararr[chararr_count] = c;
                    chararr_count++;
                    break;
                case 12:
                case 13:
                    *//* 110x xxxx   10xx xxxx*//*
                    count += 2;
                    if (count > utfLen) {
                        throw new RuntimeException("malformed input: partial character at end");
                    }
                    char2 = (char) ((bytearr[count - 1] + 256) % 256);
                    if ((char2 & 0xC0) != 0x80) {
                        throw new RuntimeException("malformed input around byte " + count);
                    }
                    chararr[chararr_count] = (char) (c & 0x1F << 6 | char2 & 0x3F);
                    chararr_count++;
                    break;
                case 14:
                    *//* 1110 xxxx  10xx xxxx  10xx xxxx*//*
                    count += 3;
                    if (count > utfLen) {
                        throw new RuntimeException("malformed input: partial character at end");
                    }
                    char2 = (char) ((bytearr[count - 2] + 256) % 256);
                    char3 = (char) ((bytearr[count - 1] + 256) % 256);
                    if ((char2 & 0xC0) != 0x80 || (char3 & 0xC0) != 0x80) {
                        throw new RuntimeException("malformed input around byte " + (count - 1));
                    }
                    chararr[chararr_count] = (char) (c & 0x0F << 12 | char2 & 0x3F << 6 | char3 & 0x3F << 0);
                    chararr_count++;
                    break;
                default:
                  *//* 10xx xxxx,  1111 xxxx *//*
                    throw new RuntimeException("malformed input around byte " + count);
            }
        }
        char[] res = new char[chararr_count];
        for (int i = 0; i < chararr_count; i++) {
            res[i] = chararr[i];
        }
        return new String(res);
    }*/

    public String getVal() {
        return val;
    }
}
