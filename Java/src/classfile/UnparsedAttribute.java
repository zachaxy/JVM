package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:
 */
public class UnparsedAttribute extends AttributeInfo {
    String attrName;
    int attrLen;

    byte[] info;

    public UnparsedAttribute(String attrName, int attrLen) {
        this.attrName = attrName;
        this.attrLen = attrLen;
    }

    @Override
    void readInfo(ClassReader reader) {
        info = reader.readBytes(attrLen);
    }
}
