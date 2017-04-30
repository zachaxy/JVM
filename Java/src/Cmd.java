/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:Cmd解析程序
 */
public class Cmd {

    boolean helpFlag;
    boolean versionFlag;
    String cpOption;
    String clazz;  //要编译的class 文件
    String[] args; //后续选项


    Cmd parseCmd(String cmdLine) {
        //解析命令行参数,以单个或者多个空格分开
        String[] args = cmdLine.trim().split("\\s+");

        if (!args[0].equals("java")) {
            printUsage();
            return null;
        } else {
            if (args[1].equals("-help") || args.equals("-?")) {
                helpFlag = true;
            } else if (args[1].equals("-version")) {
                versionFlag = true;
            } else {
                this.args = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    this.args[i - 1] = args[i];
                }
                this.clazz = this.args[0];
            }
            return this;
        }
    }

    public void printUsage() {
        System.out.println("Usage: java [-options] class [args...]\n");
    }
}
