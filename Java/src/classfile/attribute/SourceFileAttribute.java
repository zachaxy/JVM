package classfile.attribute;

import classfile.ClassReader;
import classfile.ConstantPool;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:SourceFile是可选定长属性，只会出现在ClassFile结构中，用于指出源文件名 name
 * 这个属性也是可选的，使用 Javac -g：none 选项关闭该项信息。
 * 对于大多数情况，类名和文件名是一致的，但是只有在内部类中，如果抛出异常，并且没有生成该项，堆栈中将不会显示出错代码所属的文件名。
 */
public class SourceFileAttribute extends AttributeInfo {

    //sourcefile_index是常量池索引，指向CONSTANT_Utf8_info常量，其常量值是源码文件的文件名
    int sourceFileIndex;
    ConstantPool constantPool;

    public SourceFileAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    void readInfo(ClassReader reader) {
        sourceFileIndex = reader.readUint16();
    }

    public String getFileName() {
        return constantPool.getUtf8(sourceFileIndex);
    }
}
