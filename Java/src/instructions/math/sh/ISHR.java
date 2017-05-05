package instructions.math.sh;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 算术右移,需要扩展符号位,使用最高位填充移位后左侧的空位。
 */
public class ISHR extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        int val2 = stack.popInt();  //要移动多少bit
        int val1 = stack.popInt();  //要进行位移操作的变量
        int s = val2 & 0x1f;
        int res = val1 >> s;
        stack.pushInt(res);
    }
}
