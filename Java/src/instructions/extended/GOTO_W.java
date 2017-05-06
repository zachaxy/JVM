package instructions.extended;

import instructions.base.BranchLogic;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc: goto_w指令和goto指令的唯一区别就是索引从2字节变成了4字节
 */
public class GOTO_W implements Instruction {
    int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt32();
    }

    @Override
    public void execute(Zframe frame) {
        BranchLogic.branch(frame, offset);
    }
}
