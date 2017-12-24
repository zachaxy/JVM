package test;

import runtimedata.OperandStack;

/**
 * @author zachaxy
 * @date 17/12/24
 */
public class TestOperandStack05 {
    public static void main(String[] args) {
        OperandStack stack = new OperandStack(10);
        stack.pushInt(100);
        stack.pushInt(-100);
        stack.pushLong(2997934580L);
        stack.pushLong(-2997934580L);
        stack.pushFloat(3.1415925f);
        stack.pushDouble(2.141592678912);

        System.out.println(stack.popDouble());
        System.out.println(stack.popFloat());
        System.out.println(stack.popLong());
        System.out.println(stack.popLong());
        System.out.println(stack.popInt());
        System.out.println(stack.popInt());
    }
}
