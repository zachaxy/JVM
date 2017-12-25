package test;

import Utils.Cmd;
import classfile.ClassFile;
import classfile.MemberInfo;
import classfile.attribute.CodeAttribute;
import classpath.ClassPath;
import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;
import runtimedata.Zthread;

import java.util.Scanner;

/**
 * @author zachaxy
 * @date 17/12/25
 */
public class TestInterpreter06 {
    public static void main(String[] args) {
        System.out.println("simply test a java method as 'public static void test()' in this class");
        System.out.println("the test class file--'TestInterpreter06.class' as you see in the src/test dir");
        System.out.println("ues 'java -cp your/path TestInterpreter06'");
        System.out.println("your/path means the path where you place the 'TestInterpreter06.class'");
        System.out.println("I beg you to place the 'TestInterpreter06.class' file in your desktop or other dir ");
        System.out.println("but not in the current project path!!!");
        Scanner in = new Scanner(System.in);
        String cmdLine = in.nextLine();
        String[] cmds = cmdLine.split("\\s+");
        Cmd cmd = new Cmd(cmds);
        ClassPath classPath = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        byte[] classData = classPath.readClass(cmd.getClazz());
        ClassFile classFile = new ClassFile(classData);
        MemberInfo[] methods = classFile.getMethods();
        MemberInfo targetMethod = null;
        for (MemberInfo method : methods) {
            if (method.getName().equals("test") && method.getDescriptor().equals("()I")) {
                targetMethod = method;
                break;
            }
        }
        if (targetMethod != null) {
            //拿到Code属性,主要是拿到其中的字节码用来测试之间创建的指令是否有效
            CodeAttribute codeAttribute = targetMethod.getCodeAttribute();
            //获得执行方法所需的局部变量表和操作数栈空间
            int maxLocals = codeAttribute.getMaxLocals();
            int maxStack = codeAttribute.getMaxStack();

            //拿到code的字节码;
            byte[] byteCode = codeAttribute.getCode();

            //创建一个线程
            Zthread thread = new Zthread();
            //该线程中创建一帧
            Zframe frame = thread.createFrame(maxLocals, maxStack);
            //把该帧push到虚拟机栈中，这时候虚拟机栈中已经有一帧了
            //thead.pushFrame(frame);
            BytecodeReader reader = new BytecodeReader();
            //这里循环的条件是true,因为在调用下面的 test 方法时，会遇到return,而现在还没有实现return
            // 目前遇到为能解析的指令，会抛出异常，那么循环也就终止了，此时查看其操作数栈，观察结果
            while (true) {
                int pc = frame.getNextPC(); //这第一次frame才刚初始化，获取的pc应该是默认值0吧。
                thread.setPc(pc);
                reader.reset(byteCode, pc); //reset方法，其实是在不断的设置pc的位置。
                int opCode = reader.readUint8();
                //解析指令,创建指令,然后根据不同的指令执行不同的操作
                try {
                    Instruction instruction = InstructionFactory.createInstruction(opCode);
                    instruction.fetchOperands(reader);
                    frame.setNextPC(reader.getPc());
                    instruction.execute(frame);
                } catch (Exception e) {
                    System.out.print("return:");
                    System.out.println(frame.getOperandStack().popInt());
                    return;
                }

            }
        }else{
            System.out.println("can't load test method");
        }
    }

    public static int test() {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
