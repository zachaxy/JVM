import classfile.ClassFile;
import classfile.MemberInfo;
import classpath.ClassPath;

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
        System.out.println("classpath: " + cmd.cpOption + " class: " + cmd.clazz);
        System.out.print("args:");
        for (int i = 0; i < cmd.args.length; i++) {
            System.out.print(cmd.args[i] + " ");
        }
        System.out.println();

        String className = cmd.clazz.replace(".", "/");
        System.out.println("className: " + className);

        ClassPath cp = new ClassPath(cmd.XjreOption, cmd.cpOption);
        /*byte[] data = cp.readClass(className);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }*/

        ClassFile classFile = loadClass(className, cp);
        printClassInfo(classFile);
    }

    private static void printClassInfo(ClassFile classFile) {
       /* System.out.println("version: %v.%v\n", cf.MajorVersion(), cf.MinorVersion());
        fmt.Printf("constants count: %v\n", len(cf.ConstantPool()))
        fmt.Printf("access flags: 0x%x\n", cf.AccessFlags())
        fmt.Printf("this class: %v\n", cf.ClassName())
        fmt.Printf("super class: %v\n", cf.SuperClassName())
        fmt.Printf("interfaces: %v\n", cf.InterfaceNames())
        fmt.Printf("fields count: %v\n", len(cf.Fields()))
        for _, f := range cf.Fields() {
            fmt.Printf(" %s\n", f.Name())
        }
        fmt.Printf("methods count: %v\n", len(cf.Methods()))
        for _, m := range cf.Methods() {
            fmt.Printf(" %s\n", m.Name())
        }*/
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
}
