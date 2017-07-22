package instructions.base;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:表示跳转指令，Offset字段存放跳转偏移量。
 * 这个类是指令的子类,只负责读取offset,和BranchLogic类区别是:
 * BranchLogic负责将BranchInstruction拿到的offset值,从新计算pc,并赋值给Frame.pc;
 */
public abstract class BranchInstruction implements Instruction {
    public int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt16();
    }
}
