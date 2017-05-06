package instructions.control;

import instructions.base.BranchLogic;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 如果case值可以编码成一个索引表(case中的数值是连续的)，则实现成tableswitch指令
 */
public class TABLE_SWITCH implements Instruction {
    int defaultOffset;
    //low和high记录case的取值范围
    int low;
    int high;
    //jumpOffsets是一个索引表，里面存放high-low+1个int值,，对应各种case情况下，执行跳转所需的字节码偏移量
    int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //tableswitch指令操作码的后面有0~3字节的padding，以保证 defaultOffset在字节码中的地址是4的倍数
        reader.skipPadding();
        defaultOffset = reader.readInt32();
        low = reader.readInt32();
        high = reader.readInt32();
        int jumpOffsetsCount = high - low + 1;
        jumpOffsets = reader.readInt32s(jumpOffsetsCount);
    }

    @Override
    public void execute(Zframe frame) {
        int index = frame.getOperandStack().popInt();
        int offset;
        if ((index >= low) && (index <= high)) {
            offset = jumpOffsets[index - low];
        } else {
            offset = defaultOffset;
        }

        BranchLogic.branch(frame, offset);
    }
}
