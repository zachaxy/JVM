package znative.java.lang;

import runtimedata.Zframe;
import runtimedata.heap.StringPool;
import runtimedata.heap.Zclass;
import runtimedata.heap.ZclassLoader;
import runtimedata.heap.Zobject;
import znative.NativeMethod;

/**
 * @author zachaxy
 * @date 18/1/2
 */
public class Nclass {

    // static native Class<?> getPrimitiveClass(String name);
    // (Ljava/lang/String;)Ljava/lang/Class;
    // 该方法是获取基本类型的类对象;
    public static class getPrimitiveClass implements NativeMethod {
        @Override
        public void run(Zframe frame) {
            Zobject nameObj = frame.getLocalVars().getRef(0);
            String name = StringPool.realString(nameObj);
            ZclassLoader classLoader = frame.getMethod().getClazz().getLoader();
            Zobject jObject = classLoader.loadClass(name).getjObject();
            frame.getOperandStack().pushRef(jObject);
        }
    }

    public static class getName0 implements NativeMethod {
        @Override
        public void run(Zframe frame) {
            Zobject self = frame.getLocalVars().getRef(0);
            Zclass clazz = (Zclass) self.extra;
            String name = clazz.getJavaName();
            Zobject nameObj = StringPool.jString(clazz.getLoader(), name);
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    public static class desiredAssertionStatus0 implements NativeMethod {
        @Override
        public void run(Zframe frame) {
            frame.getOperandStack().pushBoolean(false);
        }
    }

    // public native boolean isArray();
// ()Z
//    func isArray(frame *rtda.Frame) {
//        vars := frame.LocalVars()
//        this := vars.GetThis()
//        class := this.Extra().(*heap.Class)
//        stack := frame.OperandStack()
//        stack.PushBoolean(class.IsArray())
//    }
    public static class isArray implements NativeMethod {
        @Override
        public void run(Zframe frame) {
            Zobject self = frame.getLocalVars().getRef(0);
            Zclass clazz = (Zclass) self.extra;
            frame.getOperandStack().pushBoolean(clazz.isArray());
        }
    }

}
