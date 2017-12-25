package instructions.base;

import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 每个指令都会实现该接口，所有的指令逻辑都是先从字节码数组中取数据，然后执行各自的逻辑
 */
public interface Instruction {
    //从字节码中提取操作数
    void fetchOperands(BytecodeReader reader);

    //执行指令逻辑
    void execute(Zframe frame);
}
