package instructions.math.sh;

import instructions.base.NoOperandsInstruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 逻辑右移,或叫无符号右移运算符“>>>“只对位进行操作，没有算术含义，它用0填充左侧的空位。
 */
public class IUSHR extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        int val2 = stack.popInt();  //要移动多少bit
        int val1 = stack.popInt();  //要进行位移操作的变量
        int s = val2 & 0x1f;
        int res = val1 >>> s;
        stack.pushInt(res);
    }
}
