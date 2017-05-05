package instructions.base;

import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public interface Instruction {
    //从字节码中提取操作数
    void fetchOperands(BytecodeReader reader);

    //执行指令逻辑
    void execute(Zframe frame);
}
