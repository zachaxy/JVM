package runtimedata.heap;

/**
 * @author zachaxy
 * @date 17/12/25
 */
public class MethodLookup {
    public static Zmethod LookupMethodInClass(Zclass clazz, String name, String descriptor) {
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

    public static Zmethod LookupMethodInInterfaces(Zclass[] ifaces, String name, String descriptor) {
        for (Zclass iface : ifaces) {
            for (Zmethod method : iface.methods) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
            Zmethod method = LookupMethodInInterfaces(iface.interfaces, name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }
}
