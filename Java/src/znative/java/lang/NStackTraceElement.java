package znative.java.lang;

/**
 * @author zachaxy
 * @date 18/1/3
 * desc:虚拟机栈信息
 */

public class NStackTraceElement {
    String fileName;//类所在的java文件
    String className;//声明方法的类名
    String methodName;//调用方法名
    int lineNumber;//出现exception的行号

    public NStackTraceElement(String fileName, String className, String methodName, int lineNumber) {
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }
}
