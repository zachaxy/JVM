package Utils;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc:Cmd解析程序
 */
public class Cmd {

    private boolean isRightFmt = true;     //是否是正确的格式;
    private boolean isRightOpt = true;     //是否是正确的格式;
    private boolean helpFlag;        //是否是help 查看帮助
    private boolean versionFlag;    //是否是查看版本
    private String cpOption = "";  //classPath 的路径;          java -cp(-classpath) xxx
    /*使用 -Xjre 的选项:这是一个非标准的选项,java命令中是没有的,使用这个选项目的是用来指定启动类路径来寻找和加载Java标准库中的类
    即JAVA_HOME/jre的路径；
    这里要注意的是，如果真的要指定XjreOption，那么其路径值必须要用双引号包含起来
    */
    private String XjreOption = "";
    private String clazz;  //包含main方法的class文件;
    private String[] args; //执行clazz文件需要的参数


    public Cmd(String cmdLine) {
        parseCmd(cmdLine);
    }

    public Cmd(String[] strs) {
        parseCmd(strs);
    }

    public void parseCmd(String[] args) {
        int classNameIndex = 1;
        //参数必须大于等于2，否则不合法。最简短的java命令：java -version
        if (args.length < 2) {
            isRightFmt = false;
            return;
        }
        //首先判断开头是不是 java ,如果连这个都不是,直接退出吧,提示正确的使用方法;
        if (!"java".equals(args[0])) {
            isRightFmt = false;
        } else {
            if ("-help".equals(args[1]) || "-?".equals(args[1])) {
                helpFlag = true;
            } else if ("-version".equals(args[1])) {
                versionFlag = true;
            } else if ("-cp".equals(args[1]) || "classpath".equals(args[1])) {
                if (args.length < 4) {
                    //如果走到这一步,那么命令行必定是java -cp aa/bb test 11 22 33 的形式,所以应该至少有4项;
                    isRightFmt = false;
                }
                classNameIndex = 3;
                this.cpOption = args[2];
            } else if ("-Xjre".equals(args[1])) {
                if (args.length < 4) {
                    //如果走到这一步,那么命令行必定是java -Xjre "C:\Program Files\Java\jdk1.8.0_20\jre" java.lang.Object 的形式,所以应该至少有4项;
                    isRightOpt = false;
                }
                classNameIndex = 3;
                this.XjreOption = args[2];
            } else if (args[1].startsWith("-")) {
                isRightFmt = false;
            }

            this.clazz = args[classNameIndex];
            this.args = new String[args.length - classNameIndex - 1];
            int argsIndex = classNameIndex + 1;
            for (int i = argsIndex; i < args.length; i++) {
                this.args[i - argsIndex] = args[i];
            }
        }
    }

    private void parseCmd(String cmdLine) {
        //TODO:解析命令行参数,以单个或者多个空格分开,这种方式可能不行,因为如果输入的是 文件夹名字 中间有空格也就分开了...
        String[] args = cmdLine.trim().split("\\s+");
        parseCmd(args);
    }



    public void printUsage() {
        System.out.println("Usage: java [-options] class [args...]");
        System.out.println("Specially,we don't support the path that contains space!");
    }

    public boolean isRightFmt() {
        return isRightFmt;
    }

    public boolean isRightOpt() {
        return isRightOpt;
    }

    public boolean isHelpFlag() {
        return helpFlag;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public String getXjreOption() {
        return XjreOption;
    }

    public String getClazz() {
        return clazz;
    }

    public String[] getArgs() {
        return args;
    }
}
