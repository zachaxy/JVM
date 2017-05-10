/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:Cmd解析程序
 */
public class Cmd {

    boolean isRightFmt = true;     //是否是正确的格式;
    boolean helpFlag;        //是否是help 查看帮助
    boolean versionFlag;    //是否是查看版本
    String cpOption = "";  //classPath 的路径;          java -cp(-classpath) xxx
    //使用 -Xjre 的选项:这是一个非标准的选项,java命令中是没有的,使用这个选项目的是用来指定启动类路径来寻找和加载Java标准库中的类
    String XjreOption = "";
    String clazz;  //要编译的class 文件;
    String[] args; //执行clazz文件需要的参数


    public Cmd(String cmdLine) {
        parseCmd(cmdLine);
    }

    public Cmd(String[] strs) {
        parseCmd(strs);
    }

    public void parseCmd(String[] args) {
        int index = 1;

        if (args.length < 2) {
            isRightFmt = false;
            return;
        }
        //首先判断开头是不是 java ,如果连这个都不是,直接退出吧,提示正确的使用方法;
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
            } else if (args[1].equals("-Xjre")) {
                if (args.length < 4) {
                    //如果走到这一步,那么命令行必定是java -Xjre "C:\Program Files\Java\jdk1.8.0_20\jre" java.lang.Object 的形式,所以应该至少有4项;
                    isRightFmt = false;
                }
                index = 4;
                this.XjreOption = args[2];
            }

            this.clazz = args[index - 1];
            this.args = new String[args.length - index];
            for (int i = index; i < args.length; i++) {
                this.args[i - index] = args[i];
            }
        }
    }

    private void parseCmd(String cmdLine) {
        //解析命令行参数,以单个或者多个空格分开,这种方式可能不行,因为如果输入的是 文件夹名字 中间有空格也就分开了...
        String[] args = cmdLine.trim().split("\\s+");
        parseCmd(args);
    }


    String[] split(String s) {
        String[] args = s.trim().split("\\s+");
        String[] res = new String[args.length];
        int count = 0;
        for (int i = 0; i < args.length; i++) {

        }

        int start = 0;
        while (start < args.length) {
            if (args[start].startsWith("\"")) {
                int end = findEnd(args, start);
            } else {
                count++;
                start++;
            }
        }
        return null;
    }

    private int findEnd(String[] args, int index) {
        int start = index;
        for (; index < args.length; index++) {
            if (args[index].endsWith("\"")) {
                return index;
            }
        }
        return -1;
    }

    public void printUsage() {
        System.out.println("Usage: java [-options] class [args...]\n");
    }
}
