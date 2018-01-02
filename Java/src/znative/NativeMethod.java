package znative;

import runtimedata.Zframe;

/**
 * @author zachaxy
 * @date 17/12/31
 * native native方法统一实现的接口
 */
public interface NativeMethod {
    public void run(Zframe frame);
}
