package instructions.math.iinc;

import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.LocalVars;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: iinc指令给局部变量表中的int变量增加常量值，局部变量表索引和常量值都由指令的操作数提供。
 */
public class IINC implements Instruction {
    public int index;
    public int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUint8();
        offset = reader.readInt8();
    }

    @Override
    public void execute(Zframe frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(index);
        val += offset;
        localVars.setInt(index, val);
    }
}
