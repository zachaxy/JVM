package znative;

import runtimedata.Zframe;

import java.util.HashMap;

/**
 * @author zachaxy
 * @date 17/12/31
 * desc：native 方法注册中心，所有的 native 方法都要在注册中心进行注册
 */
public class RegisterCenter {
    private static HashMap<String, NativeMethod> nativeMethods = new HashMap<>();

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod nativeMethod) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        nativeMethods.put(key, nativeMethod);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        if (nativeMethods.containsKey(key)) {
            return nativeMethods.get(key);
        }

        if ("()V".equals(methodDescriptor)) {
            if ("registerNatives".equals(methodName) || "initIDs".equals(methodName)) {
                //返回一个空的方法执行体 emptyNativeMethod
                return new NativeMethod() {
                    @Override
                    public void run(Zframe frame) {

                    }
                };
            }
        }

        return null;
    }

    //对外供 JVM 启动后的唯一接入口，JVM 启动后应该立即调用 RegisterCenter 的 init 方法
    public static void init() {

    }
}
