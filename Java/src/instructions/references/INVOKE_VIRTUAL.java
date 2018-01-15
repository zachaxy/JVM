package instructions.references;

import instructions.base.Index16Instruction;
import instructions.base.MethodInvokeLogic;
import runtimedata.Zframe;
import runtimedata.heap.*;

/**
 * @author zachaxy
 * @date 17/12/26
 * desc:调用虚方法,一般用在多态;
 */
public class INVOKE_VIRTUAL extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        //调用该方法所在的类
        Zclass currentClass = frame.getMethod().getClazz();
        RuntimeConstantPool runtimeConstantPool = currentClass.getRuntimeConstantPool();
        //通过index,拿到方法符号引用,虚方法(用到了多态),这个方法引用指向的其实是父类的
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        //将方法引用转换为方法
        //这一步拿到解析后的resolvedMethod主要是用来做下面权限的验证;
        //而真正的resolvedMethod是在下面拿到真正的调用者,再次解析到的methodToBeInvoked
        Zmethod resolvedMethod = methodRef.resolvedMethod();

        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError(resolvedMethod.getName() + " in unstatic context");
        }

        //从操作数栈中获取调用该非静态方法的引用;参数的传递是从当前frame的操作数栈中根据参数个数,完整的拷贝到调用frame的本地变量表中;
        Zobject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new NullPointerException("called " + resolvedMethod.getName() + " on a null reference!");
        }

        //验证protected的方法的调用权限
        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSuperClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {

            if (!(ref.getClazz().isArray() && "clone".equals(resolvedMethod.getName()))) {
                throw new IllegalAccessError(resolvedMethod.getName() + "called in " + ref.getClazz().thisClassName);
            }
        }


        //相对于invokespecial,本指令还多了这一步,因为ref才是真正的调用者
        //而这次解析到的才是真正的method,这是多态的核心!
        Zmethod methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.getClazz(),
                methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError(methodToBeInvoked.getName());
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
