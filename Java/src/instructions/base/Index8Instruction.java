package instructions.base;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:存储和加载类指令需要根据索引存取局部变量表，索引由单字节操作数给出
 */
public abstract class Index8Instruction implements Instruction {
    public int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint8();
    }
}
