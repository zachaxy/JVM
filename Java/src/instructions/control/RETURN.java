package instructions.control;

import instructions.base.NoOperandsInstruction;
import runtimedata.Zframe;

/**
 * @author zachaxy
 * @date 17/12/27
 * return 指令；没有具体的返回值，用在 void 类型的方法中，这种方法即使不在 Java 代码中写 return 语句
 * 编译器也会自动在方法的结尾添加一条 return 指令；
 */
public class RETURN extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        frame.getThread().popFrame();
    }
}
