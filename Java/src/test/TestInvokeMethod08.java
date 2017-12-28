package test;

import Utils.Cmd;
import classpath.ClassPath;
import instructions.InstructionFactory;
import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import runtimedata.Zframe;
import runtimedata.Zthread;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZclassLoader;
import runtimedata.heap.Zmethod;

import java.util.Scanner;

/**
 * @author zachaxy
 * @date 17/12/27
 */
public class TestInvokeMethod08 implements Runnable {
    public static void main(String[] args) {
        System.out.println("the same as testInterpreter06!");
        Scanner in = new Scanner(System.in);
        // java -cp /Users/zachaxy/TestClassFiles  TestInvokeMethod08
        String cmdLine = in.nextLine();
        Cmd cmd = new Cmd(cmdLine);
        ClassPath classPath = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        ZclassLoader classLoader = new ZclassLoader(classPath);
        Zclass testClass = classLoader.loadClass(cmd.getClazz());
        Zmethod testMethod = testClass.getMethod("test", "()I");

        //初始化栈帧
        Zthread thread = new Zthread();
        //手动添加一帧，用来测试返回值；该帧
        Zframe hackFrame = thread.createFrame(2, 2);
        thread.pushFrame(hackFrame);

        Zframe frame = thread.createFrame(testMethod);
        thread.pushFrame(frame);
        Zframe outFrame = frame;
        //解释器执行
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            frame = thread.getCurrentFrame();
            if (frame == hackFrame) {
                break;
            }
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
                if (thread.isStackEmpty()) {
                    if (!frame.getOperandStack().isEmpty()) {
                        System.out.println("return: " + frame.getOperandStack().popInt());
                    }
                    break;
                }
                if (frame == outFrame) {
                    System.out.println("current instruction: " + pc + ": " + instruction.getClass().getSimpleName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return;
            }
        }
        if (!hackFrame.getOperandStack().isEmpty()) {
            int returnVar = hackFrame.getOperandStack().popInt();
            System.out.println("return: " + returnVar);
        }
    }

    public static int test() {
        TestInvokeMethod08 test = new TestInvokeMethod08(); //new -> dup ->invokespecial
        test.instanceMethod();  //invokespecial
        test.equals(null);     //invokespecial
        test.run();             //invokevirtual
        ((Runnable) test).run();//invokeinterface
        int i = staticMethod(5); //invokestatic
        return i;
    }


    public static int staticMethod(int n) {
        if (n <= 1) {
            return n;
        }
        return staticMethod(n - 1) + staticMethod(n - 2);
    }

    private void instanceMethod() {
    }

    @Override
    public void run() {

    }
}
