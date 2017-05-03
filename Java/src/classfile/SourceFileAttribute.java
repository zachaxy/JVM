package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:SourceFile是可选定长属性，只会出现在ClassFile结构中，用于指出源文件名 name
 */
public class SourceFileAttribute extends AttributeInfo {

    //sourcefile_index是常量池索引，指向CONSTANT_Utf8_info常量
    int sourceFileIndex;
    ConstantPool constantPool;

    public SourceFileAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    void readInfo(ClassReader reader) {
        sourceFileIndex = reader.readUint16();
    }

    public String getFileName(){
        return constantPool.getUtf8(sourceFileIndex);
    }
}
