package test;

import Utils.Cmd;

import java.util.Scanner;

/**
 * @author zhangxin
 * @date 2017/12/22 0022
 */
public class TestCmd01 {
    public static void main(String[] args) {
        System.out.println("Usage: java [-options] class [args...]");
        System.out.println("Specially,we don't support the path that contains space!");

        Scanner in = new Scanner(System.in);
        String cmdLine = in.nextLine();
        String[] cmds = cmdLine.split("\\s+");
        Cmd cmd = new Cmd(cmds);
        if (!cmd.isRightFmt()) {
            System.out.println("Unrecognized command!");
            cmd.printUsage();
        } else if(!cmd.isRightOpt()){
            System.out.println("Unrecognized option: "+cmds[1]);
            cmd.printUsage();
        }else {
            if (cmd.isVersionFlag()) {
                System.out.println("java version \"1.8.0_20\"\n"
                        + "Java(TM) SE Runtime Environment (build 1.8.0_20-b26)\n"
                        + "Java HotSpot(TM) 64-Bit Server VM (build 25.20-b23, mixed mode)");
            } else if (cmd.isHelpFlag()) {
                cmd.printUsage();
            } else {
                System.out.println("cmd pared successful!");
                for (int i = 0; i < cmd.getArgs().length; i++) {
                    System.out.println(cmd.getArgs()[i]);
                }
            }
        }
    }
}
