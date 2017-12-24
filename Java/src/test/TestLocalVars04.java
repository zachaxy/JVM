package test;

import runtimedata.LocalVars;

/**
 * @author zachaxy
 * @date 17/12/24
 */
public class TestLocalVars04 {

    public static void main(String[] args) {
        LocalVars localVars = new LocalVars(10);
        localVars.setInt(0,100);
        localVars.setInt(1,-100);
        localVars.setLong(2,2997934580L);
        localVars.setLong(4,-2997934580L);
        localVars.setFloat(6,3.1415926f);
        localVars.setDouble(7,2.141592678912);
        System.out.println(localVars.getInt(0));
        System.out.println(localVars.getInt(1));
        System.out.println(localVars.getLong(2));
        System.out.println(localVars.getLong(4));
        System.out.println(localVars.getFloat(6));
        System.out.println(localVars.getDouble(7));

    }
}
