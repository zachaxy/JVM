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
        args[1] = "-Xjre";
        args[2] = "C:\\Program Files\\Java\\jdk1.8.0_20\\jre";
        args[3] = "java.lang.Object";
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

        String className = cmd.clazz.replace(".", "/");
        ClassPath cp = new ClassPath(cmd.XjreOption, cmd.cpOption);
        byte[] data = cp.readClass(className);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }

    }
}
