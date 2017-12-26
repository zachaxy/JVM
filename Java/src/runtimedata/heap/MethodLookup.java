package runtimedata.heap;

/**
 * @author zachaxy
 * @date 17/12/25
 */
public class MethodLookup {
    public static Zmethod lookupMethodInClass(Zclass clazz, String name, String descriptor) {
        Zclass c = clazz;
        while (c != null) {
            for (Zmethod method : c.methods) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
            c = c.superClass;
        }
        return null;
    }

    public static Zmethod lookupMethodInInterfaces(Zclass[] ifaces, String name, String descriptor) {
        for (Zclass iface : ifaces) {
            for (Zmethod method : iface.methods) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
            Zmethod method = lookupMethodInInterfaces(iface.interfaces, name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }
}
