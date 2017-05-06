package instructions.control;

import instructions.base.BranchLogic;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:如果case值不可以编码成一个索引表(case中的数值不是连续的)，则实现成lookupswitch指令
 */
public class LOOKUP_SWITCH implements Instruction {
    int defaultOffset;
    int npairs;
    //matchOffsets有点像Map，它的key是case值，value是跳转偏移,但是并没有实现成map,而是用数组代替,两个连续的数位key-value;
    int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt32();
        npairs = reader.readInt32();
        matchOffsets = reader.readInt32s(npairs * 2);
    }

    @Override
    public void execute(Zframe frame) {
        int key = frame.getOperandStack().popInt();
        for (int i = 0; i < npairs * 2; i += 2) {
            if (matchOffsets[i] == key) {
                int offset = matchOffsets[i + 1];
                BranchLogic.branch(frame, defaultOffset);
                return;
            }
        }
        BranchLogic.branch(frame, defaultOffset);
    }
}
