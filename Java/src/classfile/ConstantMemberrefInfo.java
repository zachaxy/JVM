package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/2 0002.
 * Desc:
 */
public class ConstantMemberrefInfo extends ConstantInfo {
    ConstantPool constantPool;
    int classIndex;
    int  nameAndTypeIndex;

    @Override
    void readInfo(ClassReader reader) {
        classIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

    String getClassName(){
        return constantPool.getClassName(classIndex);
    }

    String getName(){
        return constantPool.getName(nameAndTypeIndex);
    }

    String getDescriptor(){
        return constantPool.getType(nameAndTypeIndex);
    }
}
