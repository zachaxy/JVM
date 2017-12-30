package instructions.references;

import instructions.base.BytecodeReader;
import instructions.base.Index16Instruction;
import runtimedata.OperandStack;
import runtimedata.Zframe;
import runtimedata.heap.ClassRef;
import runtimedata.heap.RuntimeConstantPool;
import runtimedata.heap.Zclass;
import runtimedata.heap.Zobject;

import java.util.LinkedList;

/**
 * @author zachaxy
 * @date 17/12/30
 * desc:创建多维数组:基础类型+引用类型的都是这个指令;
 * 这里以 int[][][] arr = new int[3][4][5] 为例
 * 其产生的字节码为：
 * iconst_3
 * iconst_4
 * iconst_5
 * multianewarray #5 ([[[I,3)
 * <p>
 * 首先将三维数组各个维度压入操作数栈，栈顶向下一次为： 5，4，3
 * multianewarray指令 获取两个操作数，第一个 index 表示运行时常量池的类符号引用，其类名为[[[I
 * 接着获取第二个操作数3，表明这是一个三维数组
 * <p>
 * multianewarray指令的执行：首先将获取到的类符号引用转为直接引用：转换依然是用 classloader，因为是数组类，
 * 所以不用从 class 文件中读取字节流，而是直接创建一个 class，该 class 只需将类名指定为 [[[I，即可。
 * 接下来依次从操作数栈中弹出三个整数，表示该多维数组每一维的大小；然后开始创建该多维数组的对象
 * <p>
 * 多维数组对象的创建过程：此时拿到的类名是：[[[I，各个维的大小是3，4，5；
 * 首先利用数组类[[[I,创建第一维 arr1 ，大小为3，（多维数组对外表现的就是一维数组，只不过该数组中的元素依然是数组。）
 * 接下来创建 arr1 中的每一个元素，其元素也是数组，我们称之为第2维，arr2（arr1中的三个元素都是 arr2）
 * arr2 此时的类型为 [[I，依然需要用 classloader 进行加载，然后创建；
 * 接下来创建 arr2 中的每一个元素，其元素还是数组，我们称之为第3维，arr3（arr2中的三个元素都是 arr3）
 * arr3 此时的类型为 [I,依然需要用classloader 进行加载，然后创建；
 * 最终将创建好的 arr1，压入操作数栈，结束！
 * <p>
 * <p>
 * 当counts==2的时候,即if len(counts)>1,在if方法体内,调用了newMultiDimensionalArray方法,但是传入的counts是1,
 * 而在newMultiDimensionalArray方法刚一开始的时候,就创建了一维数组;
 * newMultiDimensionalArray方法没有问题;就是看着有点绕;
 */
public class MULTI_ANEW_ARRAY extends Index16Instruction {
    //数组的纬度
    private int dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        super.fetchOperands(reader);
        dimensions = reader.readUint8();
    }

    @Override
    public void execute(Zframe frame) {
        RuntimeConstantPool runtimeConstantPool = frame.getMethod().getClazz().getRuntimeConstantPool();
        //多维数组的类符号引用
        ClassRef classRef = (ClassRef) runtimeConstantPool.getRuntimeConstant(this.index).getValue();
        //解析该数组引用，就能得到该多维数组的类
        Zclass arrClazz = classRef.resolvedClass();

        OperandStack operandStack = frame.getOperandStack();
        //各个维的大小;
        LinkedList<Integer> list = popAndCheckCounts(operandStack, dimensions);
        Zobject arr = newMultiDimensionalArray(list, arrClazz);
        operandStack.pushRef(arr);
    }

    //从操作数栈中弹出dimensions个整数,分别表示各个维度的大小
    private LinkedList<Integer> popAndCheckCounts(OperandStack operandStack, int dimensions) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = dimensions - 1; i >= 0; i--) {
            int count = operandStack.popInt();
            if (count < 0) {
                throw new NegativeArraySizeException(count + " for " + i + "th");
            }
            list.add(count);
        }
        return list;
    }

    //创建多维数组
    private Zobject newMultiDimensionalArray(LinkedList<Integer> list, Zclass arrClazz) {
        int count = list.peek();
        //首先创建第一纬(行);后面几纬整体作为一个类相继往直前的数组中填充; arrClass只是类名,可以创建其一维数组
        Zobject arrObj = arrClazz.newArray(count);
        if (list.size() > 1) {
            Zobject[] refs = arrObj.getRefs();
            list.poll();
            for (int i = 0; i < refs.length; i++) {
                //在创建新的数组，传入的 class 是上面一层 class 的名称，退去了一层 [
                refs[i] = newMultiDimensionalArray(list, arrClazz.getComponentClass());
            }
        }
        return arrObj;
    }
}
