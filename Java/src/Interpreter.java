import classfile.CodeAttribute;
import classfile.MemberInfo;
import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;
import runtimedata.Zthread;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class Interpreter {
    public static void interpret(MemberInfo memberInfo) {
        //拿到Code属性,这个属性是是方法所特有的
        CodeAttribute codeAttribute = memberInfo.getCodeAttribute();

        //获得执行方法所需的局部变量表和操作数栈空间
        int maxLocals = codeAttribute.getMaxLocals();
        int maxStack = codeAttribute.getMaxStack();

        //拿到code的字节码;
        byte[] byteCode = codeAttribute.getCode();

        //创建一个线程
        Zthread thead = new Zthread();
        //该线程中创建一帧
        Zframe frame = thead.createFrame(maxLocals, maxStack);
        //把该帧push到虚拟机栈中，这时候虚拟机栈中已经有一帧了
        thead.pushFrame(frame);

/*
我们的解释器目前还没有办法优雅地结束运行。
因为每个方法的最后一条指令都是某个return指令，而还没有实现return指令，所以方法在执行过程中必定会出现错误,目前的做法是遇到return命令就手动抛出一个异常
然后在catch中捕获异常,局部变量表和操作数栈的内容打印出来，以此来观察方法的执行结果
*/
        try {
            loop(thead, byteCode);
        } catch (Exception e) {
            System.out.println("return");
            System.out.println(frame.getLocalVars());
            System.out.println(frame.getOperandStack());
        }
    }


    //循环执行“计算pc、解码指令、执行指令”这三个步骤，直到遇到错误
    private static void loop(Zthread thread, byte[] byteCode) {
        Zframe frame = thread.popFrame();   //得到栈顶的帧。
        BytecodeReader reader = new BytecodeReader();

        //这里循环的条件是true,因为在解析指令的时候会遇到return,而现在还没有实现return,所以遇到return 直接抛出异常,那么循环也就终止了
        while (true) {
            int pc = frame.getNextPC(); //这第一次frame才刚初始化，获取的pc应该是默认值0吧。
            thread.setPc(pc);
            reader.reset(byteCode, pc); //reset方法，其实是在不断的设置pc的位置。
            int opCode = reader.readUint8();
            //解析指令,创建指令,然后根据不同的指令执行不同的操作
            Instruction instruction = InstructionFactory.createInstruction(opCode);
            instruction.fetchOperands(reader);
            frame.setNextPC(reader.getPc());

            System.out.printf("pc: %2d, inst: %s \n", pc, instruction.getClass().getSimpleName());
            instruction.execute(frame);
        }
    }
}
