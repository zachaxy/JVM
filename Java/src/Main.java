import Utils.Cmd;
import classfile.ClassFile;
import classfile.MemberInfo;
import classpath.ClassPath;
import runtimedata.LocalVars;
import runtimedata.OperandStack;

import java.util.Scanner;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:
 */
public class Main {
    public static void main(String[] args) {
       /* Scanner in = new Scanner(System.in);
        String argsLine = in.nextLine();*/
/*        args = new String[4];
        args[0] = "java";
//        args[1] = "-cp";
        *//*
        args[1] = "-Xjre";
        args[2] = "C:\\Program Files\\Java\\jdk1.8.0_20\\jre";
        args[3] = "java.lang.String";*//*
        *//*args[2] = "E:\\Go\\jvmgo-book-master\\v1\\code\\java\\example\\src\\main\\java\\jvmgo\\book\\ch03";
        args[3] = "ClassFileTest";*//*
        args[1] = "-cp";
        args[2] = "E:\\go_workspace\\code\\java\\example\\src\\main\\java\\jvmgo\\book\\ch05";
        args[3] = "GaussTest";*/


        Scanner in = new Scanner(System.in);
        String cmdLine = in.nextLine();
        String[] cmds = cmdLine.split("\\s+");
        Cmd cmd = new Cmd(cmds);
        if (!cmd.isRightFmt()) {
            System.out.println("Unrecognized command!");
            cmd.printUsage();
        } else if (!cmd.isRightOpt()) {
            System.out.println("Unrecognized option: " + cmds[1]);
            cmd.printUsage();
        } else {
            if (cmd.isVersionFlag()) {
                System.out.println("java version \"1.8.0_20\"\n"
                        + "Java(TM) SE Runtime Environment (build 1.8.0_20-b26)\n"
                        + "Java HotSpot(TM) 64-Bit Server VM (build 25.20-b23, mixed mode)");
            } else if (cmd.isHelpFlag()) {
                cmd.printUsage();
            } else {
                startJVM(cmd);
            }
        }

    }

    private static void startJVM(Cmd cmd) {
        System.out.println("classpath: " + cmd.getCpOption() + " class: " + cmd.getClazz());
        System.out.print("方法的参数 args:");
        for (int i = 0; i < cmd.getArgs().length; i++) {
            System.out.print(cmd.getArgs()[i] + " ");
        }
        System.out.println();

        String className = cmd.getClazz().replace(".", "/");
        System.out.println("className: " + className);

        ClassPath cp = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        /*byte[] data = cp.readClass(className);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }*/
        ClassFile classFile = loadClass(className, cp);
        System.out.println(classFile.getClassName());
        System.out.println(classFile.getSuperClassName());
        MemberInfo mainMethod = getMainMethod(classFile);
        if (mainMethod != null) {
            Interpreter.interpret(mainMethod);
        } else {
            System.out.println("Main method not found in class " + className);
        }
      /*   printClassInfo(classFile)
        Zframe frame = new Zframe(100, 100);
        testLocalVars(frame.getLocalVars());
        testOperandStack(frame.getOperandStack());*/
    }

    private static MemberInfo getMainMethod(ClassFile classFile) {
        MemberInfo[] methods = classFile.getMethods();
        for (MemberInfo method : methods) {
            if (method.getName().equals("main") && method.getDescriptor().equals("([Ljava/lang/String;)V")) {
                return method;
            }
        }
        return null;
    }

    private static void printClassInfo(ClassFile classFile) {
        System.out.println("version: " + classFile.getMajorVersion() + "." + classFile.getMinorVersion());
        System.out.println("constants count: " + classFile.getConstantPool().getConstantPoolCount());
        System.out.println("access flags: 0x%x " + classFile.getAccessFlags());
        System.out.println("this class: " + classFile.getThisClass());
        System.out.println("super class: " + classFile.getSuperClassName());
        System.out.println("----------------------------");
        System.out.println("interfaces");
        for (int i = 0; i < classFile.getInterfaces().length; i++) {
            System.out.println(classFile.getInterfaceNames());
        }
        System.out.println("----------------------------");
        String[] interfaceNames = classFile.getInterfaceNames();
        for (String interfaceName : interfaceNames) {
            System.out.println(interfaceName);
        }
        System.out.println("----------------------------");
        System.out.println("fields count" + classFile.getFields().length);
        for (MemberInfo filed : classFile.getFields()) {
            System.out.println(filed.getName());
        }
        System.out.println("----------------------------");
        System.out.println("methods count: " + classFile.getMethods().length);
        for (MemberInfo method : classFile.getMethods()) {
            System.out.println(method.getName());
        }
    }

    private static ClassFile loadClass(String className, ClassPath cp) {
        byte[] data = cp.readClass(className);
        return new ClassFile(data);
    }


    static void testLocalVars(LocalVars vars) {
        vars.setInt(0, 100);
        vars.setInt(1, -100);
        vars.setLong(2, 2997924580L);
        vars.setLong(4, -2997924580L);
        vars.setFloat(6, 3.1415926f);
        vars.setDouble(7, 2.71828182845);
        vars.setRef(9, null);
        System.out.println(vars.getInt(0));
        System.out.println(vars.getInt(1));
        System.out.println(vars.getLong(2));
        System.out.println(vars.getLong(4));
        System.out.println(vars.getFloat(6));
        System.out.println(vars.getDouble(7));
        System.out.println(vars.getRef(9));
    }

    static void testOperandStack(OperandStack ops) {
        ops.pushInt(100);
        ops.pushInt(-100);
        ops.pushLong(2997924580L);
        ops.pushLong(-2997924580L);
        ops.pushFloat(3.1415926f);
        ops.pushDouble(2.71828182845);
        ops.pushRef(null);
        System.out.println(ops.popRef());
        System.out.println(ops.popDouble());
        System.out.println(ops.popFloat());
        System.out.println(ops.popLong());
        System.out.println(ops.popLong());
        System.out.println(ops.popInt());
        System.out.println(ops.popInt());
    }
}
