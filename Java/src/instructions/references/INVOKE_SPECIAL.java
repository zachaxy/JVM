package instructions.references;

import instructions.base.Index16Instruction;
import instructions.base.MethodInvokeLogic;
import runtimedata.Zframe;
import runtimedata.heap.*;

/**
 * @author zachaxy
 * @date 17/12/26
 * desc:调用无须动态绑定的实例方法(包括：构造函数,私有方法,通过super关键字调用的超类方法)
 * JVM特意为这种不需要动态绑定的方法创建的invokespecial,目的是为了加快方法调用(解析)的速度
 */
public class INVOKE_SPECIAL extends Index16Instruction {
    @Override
    public void execute(Zframe frame) {
        //调用该方法所在的类
        Zclass currentClass = frame.getMethod().getClazz();
        RuntimeConstantPool runtimeConstantPool = currentClass.getRuntimeConstantPool();
        //通过index,拿到方法符号引用
        MethodRef methodRef = (MethodRef) runtimeConstantPool.getRuntimeConstant(index).getValue();
        //和静态方法不同的是,要先加载方法所在的类
        Zclass resolvedClass = methodRef.resolvedClass();
        //将方法引用转换为方法
        Zmethod resolvedMethod = methodRef.resolvedMethod();

        //<init>方法必须在其对应的类进行声明,这里必须要验证类是否匹配
        if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClazz() != resolvedClass) {
            throw new NoSuchMethodError(resolvedMethod.getName());
        }

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
            throw new IllegalAccessError();
        }

        Zmethod methodToBeInvoked = resolvedMethod;
        //首先 ACC_SUPER:在jdk1.02之后编译出来的类,该标志均为真;
        //解决 super.func()的形式;但是不能是<init>方法;因为父类中可能定义了func方法,同时子类又覆盖了父类的func,
        //那么解析func的符号引用时首先能在子类中解析到,但此时显示的调用了父类的func方法,所以还需要在父类中去解析;
        if (currentClass.isSuper() &&
                resolvedClass.isSuperClassOf(currentClass) &&
                !"<init>".equals(resolvedMethod.getName())) {
            methodToBeInvoked = MethodLookup.lookupMethodInClass(currentClass.getSuperClass(),
                    methodRef.getName(), methodRef.getDescriptor());
        }

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError(methodToBeInvoked.getName());
        }
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
