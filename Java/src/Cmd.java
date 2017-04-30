/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:Cmd解析程序
 */
public class Cmd {

    boolean isRightFmt = true;     //是否是正确的格式;
    boolean helpFlag;        //是否是help 查看帮助
    boolean versionFlag;    //是否是查看版本
    String cpOption;  //classPath 的路径;
    String XjreOption;  //
    String clazz;  //要编译的class 文件;
    String[] args; //执行clazz文件需要的参数


    public Cmd(String cmdLine) {
        parseCmd(cmdLine);
    }

    private void parseCmd(String cmdLine) {
        //解析命令行参数,以单个或者多个空格分开
        String[] args = cmdLine.trim().split("\\s+");
        int index = 1;

        if (!args[0].equals("java")) {
            isRightFmt = false;
        } else {
            if (args[1].equals("-help") || args[1].equals("-?")) {
                helpFlag = true;
            } else if (args[1].equals("-version")) {
                versionFlag = true;
            } else if (args[1].equals("-cp") || args[1].equals("classpath")) {
                if (args.length < 4) {
                    //如果走到这一步,那么命令行必定是java -cp aa/bb test 11 22 33 的形式,所以应该至少有4项;
                    isRightFmt = false;
                }
                index = 4;
                this.cpOption = args[2];
            }

            this.clazz = args[index - 1];
            this.args = new String[args.length - index];
            for (int i = index; i < args.length; i++) {
                this.args[i - index] = args[i];
            }
        }
    }

    public void printUsage() {
        System.out.println("Usage: java [-options] class [args...]\n");
    }
}
