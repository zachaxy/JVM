package instructions.base;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:表示跳转指令，Offset字段存放跳转偏移量。
 */
public abstract class BranchInstruction implements Instruction {
    public int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt16();
    }
}
