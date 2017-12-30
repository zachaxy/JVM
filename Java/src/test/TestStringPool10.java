package test;

import Utils.Cmd;
import classpath.ClassPath;
import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;
import runtimedata.Zthread;
import runtimedata.heap.*;

import java.util.Scanner;

/**
 * @author zachaxy
 * @date 17/12/30
 */
public class TestStringPool10 {
    public static void main(String[] args) {
        System.out.println("the same as testInterpreter06!");
        Scanner in = new Scanner(System.in);
        // java -cp /Users/zachaxy/TestClassFiles  TestStringPool10
        String cmdLine = in.nextLine();
        Cmd cmd = new Cmd(cmdLine);
        ClassPath classPath = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        ZclassLoader classLoader = new ZclassLoader(classPath);
        Zclass testClass = classLoader.loadClass(cmd.getClazz());
        Zmethod testMethod = testClass.getMethod("test", "()V");
        //初始化栈帧
        Zthread thread = new Zthread();
        Zframe frame = thread.createFrame(testMethod);
        Zframe outFrame = frame;
        thread.pushFrame(frame);
        BytecodeReader reader = new BytecodeReader();

        while (true) {
            frame = thread.getCurrentFrame();
            int pc = frame.getNextPC();
            thread.setPc(pc);


            //decode
            reader.reset(frame.getMethod().getCode(), pc);
            int opCode = reader.readUint8();
            //解析指令,创建指令,然后根据不同的指令执行不同的操作
            try {
                Instruction instruction = InstructionFactory.createInstruction(opCode);
                instruction.fetchOperands(reader);
                frame.setNextPC(reader.getPc());
                instruction.execute(frame);
                if (frame == outFrame) {
                    System.out.println("current instruction: " + pc + ": " + instruction.getClass().getSimpleName());
                }
                if (thread.isStackEmpty()) {
                    if (!frame.getOperandStack().isEmpty()) {
                        System.out.println("return: " + frame.getOperandStack().popInt());
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return;
            }
        }

        Zobject ref = outFrame.getLocalVars().getRef(2);
        System.out.println(StringPool.realString(ref));
    }

    public static void test() {
        String s1 = "hello";
        String s2 = "hello";//new String("hello");不支持 new String 的方式，会报错！
        String s3;
        if (s1 == s2) {
            s3 = "yes";
        } else {
            s3 = "no";
        }
    }
}
