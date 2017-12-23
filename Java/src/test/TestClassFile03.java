package test;

import Utils.Cmd;
import classfile.ClassFile;
import classfile.MemberInfo;
import classfile.attribute.AttributeInfo;
import classpath.ClassPath;

import java.util.Scanner;

/**
 * @author zhangxin
 * @date 2017/12/23 0023
 * testcase as flow:
 * (1)java java.lang.String
 * (2)java -cp C:\Users\zhangxin\Desktop Hello
 */
public class TestClassFile03 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String cmdLine = in.nextLine();
        String[] cmds = cmdLine.split("\\s+");
        Cmd cmd = new Cmd(cmds);
        ClassPath classPath = new ClassPath(cmd.getXJreOption(), cmd.getCpOption());
        byte[] classData = classPath.readClass(cmd.getClazz());
        ClassFile classFile = new ClassFile(classData);
        System.out.println("classFile.getMajorVersion: " + classFile.getMajorVersion());
        System.out.println("classFile.getMinorVersion: " + classFile.getMinorVersion());
        System.out.println("classFile.getAccessFlags: " + classFile.getAccessFlags());
        System.out.println("classFile.getClassName: " + classFile.getClassName());
        System.out.println("classFile.getSuperClassName: " + classFile.getSuperClassName());
        System.out.println("interface names:");
        for (String name : classFile.getInterfaceNames()) {
            System.out.println(name);
        }
        System.out.println("---------------------");
        System.out.println("field count: " + classFile.getFields().length);
        for (MemberInfo name : classFile.getFields()) {
            System.out.println(name.getName());
        }
        System.out.println("---------------------");
        System.out.println("method count: " + classFile.getMethods().length);
        for (MemberInfo name : classFile.getMethods()) {
            System.out.println(name.getName() + ":" + name.getDescriptor());
        }
        System.out.println("---------------------");
        System.out.println("constantPool count: "+classFile.getConstantPool().getConstantPoolCount());
        System.out.println("---------------------");
        System.out.println("attribute count:"+classFile.getAttributes().length);
        for (AttributeInfo attribute:classFile.getAttributes()){
            System.out.println(attribute.getClass());
        }
    }
}
