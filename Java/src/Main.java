import Utils.Cmd;
import classfile.ClassFile;
import classfile.MemberInfo;
import classpath.ClassPath;

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

//        String className = cmd.getClazz().replace(".", "/");
//        System.out.println("className: " + className);

        ClassPath cp = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        /*byte[] data = cp.readClass(className);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }*/
        ClassFile classFile = loadClass(cmd.getClazz(), cp);
        System.out.println(classFile.getClassName());
        System.out.println(classFile.getSuperClassName());
        MemberInfo mainMethod = getMainMethod(classFile);
        if (mainMethod != null) {
            Interpreter.interpret(mainMethod);
        } else {
            System.out.println("Main method not found in class " + cmd.getClazz());
        }
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

    private static ClassFile loadClass(String className, ClassPath cp) {
        byte[] data = cp.readClass(className);
        return new ClassFile(data);
    }

}
