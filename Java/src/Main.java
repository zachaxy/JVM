import classfile.ClassFile;
import classfile.MemberInfo;
import classpath.ClassPath;
import runtimedata.LocalVars;
import runtimedata.OperandStack;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:
 */
public class Main {
    public static void main(String[] args) {
       /* Scanner in = new Scanner(System.in);
        String argsLine = in.nextLine();*/
        args = new String[4];
        args[0] = "java";
        args[1] = "-cp";
        /*
        args[1] = "-Xjre";
        args[2] = "C:\\Program Files\\Java\\jdk1.8.0_20\\jre";
        args[3] = "java.lang.String";*/
        args[2] = "E:\\Go\\jvmgo-book-master\\v1\\code\\java\\example\\src\\main\\java\\jvmgo\\book\\ch03";
        args[3] = "ClassFileTest";
        Cmd cmd = new Cmd(args);

        if (!cmd.isRightFmt) {
            cmd.printUsage();
        } else {
            if (cmd.versionFlag) {
                System.out.println("java version \"1.8.0_20\"\n"
                        + "Java(TM) SE Runtime Environment (build 1.8.0_20-b26)\n"
                        + "Java HotSpot(TM) 64-Bit Server VM (build 25.20-b23, mixed mode)");
            } else if (cmd.helpFlag || cmd.args == null) {
                cmd.printUsage();
            } else {
                startJVM(cmd);
            }
        }
    }

    static void startJVM(Cmd cmd) {
       /* System.out.println("classpath: " + cmd.cpOption + " class: " + cmd.clazz);
        System.out.print("args:");
        for (int i = 0; i < cmd.args.length; i++) {
            System.out.print(cmd.args[i] + " ");
        }
        System.out.println();

        String className = cmd.clazz.replace(".", "/");
        System.out.println("className: " + className);

        ClassPath cp = new ClassPath(cmd.XjreOption, cmd.cpOption);

        ClassFile classFile = loadClass(className, cp);
        printClassInfo(classFile);*/

        Zframe frame = new Zframe(100, 100);
        testLocalVars(frame.getLocalVars());
        testOperandStack(frame.getOperandStack());
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
