package classfile.attribute;

import classfile.ClassReader;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc: 由于精力有限，这里不可能将所有的属性都实现，只是挑重要的几个实现，其它的都直接跳过所对应的字节数即可。
 */
public class UnparsedAttribute extends AttributeInfo {
    private String attrName;
    private int attrLen;
    private byte[] info;

    public UnparsedAttribute(String attrName, int attrLen) {
        this.attrName = attrName;
        this.attrLen = attrLen;
    }

    @Override
    void readInfo(ClassReader reader) {
        info = reader.readBytes(attrLen);
    }
}
