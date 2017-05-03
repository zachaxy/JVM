package classfile;

/**
 * Author: zhangxin
 * Time: 2017/5/3 0003.
 * Desc:
 */
public class LocalVariableTableAttribute extends AttributeInfo {
    LocalVariableTableEntry[] localVariableTable;
    @Override
    void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i <localVariableTableLength ; i++) {
            localVariableTable[i] = new LocalVariableTableEntry(
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16(),
                    reader.readUint16()
            );
        }
    }

    static class LocalVariableTableEntry {
        int startPc;
        int length;
        int nameIndex;
        int descriptorIndex;
        int index;

        public LocalVariableTableEntry(int startPc, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPc = startPc;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }
    }
}
