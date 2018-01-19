package runtimedata.heap;

import classfile.MemberInfo;
import classfile.attribute.CodeAttribute;
import classfile.attribute.ExceptionsAttribute;
import classfile.attribute.LineNumberTableAttribute;

import java.util.ArrayList;

/**
 * Author: zhangxin
 * Time: 2017/5/19 0019.
 * Desc: 方法的抽象,是在class中定义的方法,包括静态的和非静态的
 */
public class Zmethod extends ClassMember {
    private int maxStack;
    private int maxLocals;
    private byte[] code;        //如果没有code属性,取值为null;不过就算是空方法也有一个return 语句;
    private ExceptionTable exceptionTable;
    private LineNumberTableAttribute lineNumberTable;
    private ExceptionsAttribute exceptions;  //由方法声明中显式throws显示的异常
    private MethodDescriptor parsedDescriptor;  //解析后的方法描述符
    private int argSlotCount;   //方法所需的参数个数;对于非静态方法,至少是1个(this)

    private Zmethod(Zclass clazz, MemberInfo classFileMethod) {
        super(clazz, classFileMethod);
        copyAttributes(classFileMethod);
        parsedDescriptor = new MethodDescriptor(this.descriptor);
        argSlotCount = calcArgSlotCount(parsedDescriptor.getParameterTypes());
        if (isNative()) {
            injectCodeAttribute(parsedDescriptor.getReturnType());
        }
    }

    //该方法用来初始化成员变量：maxStack，maxLocals，code，如果是 native 方法，是没有任何 code 字节码的；
    private void copyAttributes(MemberInfo classFileMethod) {
        CodeAttribute codeAttribute = classFileMethod.getCodeAttribute();
        if (codeAttribute != null) {
            maxStack = codeAttribute.getMaxStack();
            maxLocals = codeAttribute.getMaxLocals();
            code = codeAttribute.getCode();
            lineNumberTable = codeAttribute.lineNumberTableAttribute();
            //这一步主要是将classFile中的异常处理表(符号引用),转换为运行时的异常处理表(直接引用);主要在于catchType的转换
            exceptionTable = new ExceptionTable(codeAttribute.getExceptionTable(),
                    clazz.getRuntimeConstantPool());
        }
        exceptions = classFileMethod.getExceptionsAttribute();
    }

    private int calcArgSlotCount(ArrayList<String> args) {
        int slotCount = 0;
        //如果当前方法不是静态方法，那么其第一个参数为 ‘this’引用
        if (!isStatic()) {
            slotCount++;
        }
        for (String arg : args) {
            slotCount++;
            if ("J".equals(arg) || "D".equals(arg)) {
                slotCount++;
            }
        }
        return slotCount;
    }

    // JVM 并没有规定如何实现和调用本地方法，这里我们依然使用 JVM 栈 来执行本地方法
    // 但是本地方法中并不包含字节码，那么本地方法的调用，这里我们利用接口来实现调用对应的方法；
    // 同时 JVM 中预留了两条指令，操作码分别是 0xff 和 0xfe，下面使用 0xfe 来当前方法为表示本地方法
    // 第二个字节为本地方法的返回指令，该指令和普通方法的返回指令是一样的。
    private void injectCodeAttribute(String returnType) {
        //本地方法的操作数栈暂时为4;至少能容纳返回值
        this.maxStack = 4;
        //本地方法的局部变量表只用来存放参数,因此直接这样赋值
        this.maxLocals = this.argSlotCount;
        //接下来为本地方法构造字节码:起始第一个字节都是0xfe,用来表用这是本地方法;
        //第二个字节码则根据不同的返回值类型选择相应的xreturn的指令即可
        //不必担心下面的 byte 的强转，因为在读取字节码时，使用的是 readUint8()方法
        switch (returnType.charAt(0)) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1}; // return
                break;
            case 'L':
            case '[':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0}; // areturn
                break;
            case 'D':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf}; // dreturn
                break;
            case 'F':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae}; // freturn
                break;
            case 'J':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad}; // lreturn
                break;
            default:
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};// ireturn
        }
    }

    public static Zmethod[] makeMethods(Zclass zclass, MemberInfo[] classFileMethods) {
        Zmethod[] methods = new Zmethod[classFileMethods.length];
        for (int i = 0; i < methods.length; i++) {
            Zmethod method = new Zmethod(zclass, classFileMethods[i]);
            methods[i] = method;
        }
        return methods;
    }


    public boolean isSynchronized() {
        return 0 != (accessFlags & AccessFlag.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
        return 0 != (accessFlags & AccessFlag.ACC_BRIDGE);
    }

    public boolean isVarargs() {
        return 0 != (accessFlags & AccessFlag.ACC_VARARGS);
    }

    public boolean isNative() {
        return 0 != (accessFlags & AccessFlag.ACC_NATIVE);
    }

    public boolean isAbstract() {
        return 0 != (accessFlags & AccessFlag.ACC_ABSTRACT);
    }

    public boolean isStrict() {
        return 0 != (accessFlags & AccessFlag.ACC_STRICT);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }


    //搜索自身方法的异常处理表,如果能找到对应的异常处理项,则返回其handlerPC字段(指出负责异常处理的catch块的位置),否则返回-1
    public int findExceptionHandler(Zclass exClazz, int pc) {
        ExceptionHandler handler = exceptionTable.findExceptionHandler(exClazz, pc);
        if (handler != null) {
            return handler.handlerPc;
        }
        return -1;
    }

    public Zclass[] getExceptionTypes() {
        if (this.exceptions == null) {
            return new Zclass[0];
        }

        int[] exceptionIndexTable = exceptions.getExceptionIndexTable();
        Zclass[] exClasses = new Zclass[exceptionIndexTable.length];
        RuntimeConstantPool runtimeConstantPool = clazz.getRuntimeConstantPool();

        for (int i = 0; i < exceptionIndexTable.length; i++) {
            ClassRef classRef = (ClassRef) runtimeConstantPool.getRuntimeConstant(exceptionIndexTable[i]).getValue();
            exClasses[i] = classRef.resolvedClass();
        }

        return exClasses;
    }


    public int getLineNumber(int pc) {
        if (isNative()) {
            return -2;
        }
        if (lineNumberTable == null) {
            return -1;
        }
        return lineNumberTable.getLineNumber(pc);
    }
}
