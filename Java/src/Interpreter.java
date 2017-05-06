import classfile.CodeAttribute;
import classfile.MemberInfo;
import instructions.base.BytecodeReader;
import runtimedata.Zframe;
import runtimedata.Zthread;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class Interpreter {
    void interpret(MemberInfo memberInfo){
        CodeAttribute codeAttribute = memberInfo.getCodeAttribute();
        int maxLocals = codeAttribute.getMaxLocals();
        int maxStack = codeAttribute.getMaxStack();
        byte[] byteCode= codeAttribute.getCode();

        Zthread thead = new Zthread();
        Zframe frame = thead.createFrame(maxLocals, maxStack);

        thead.pushFrame(frame);


    }


    void  loop(Zthread thread,byte[] byteCode){
        Zframe frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();

        while (true){
            int pc = frame.getNextPC();
            thread.setPc(pc);
            reader.reset(byteCode,pc);
            int opCode = reader.readUint8();

        }
    }
}
