package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.util.Map;
import java.io.Serializable;
public class TestInvocationHandler implements InvocationHandler,Serializable{
    protected Map map;
    public TestInvocationHandler(Map map){
        this.map=map;
    }
    @Override
    public Object invoke(Object proxy, Method method,Object[] args) throws Throwable{
        System.out.println("Hook Method:"+method.getName());
        return method.invoke(this.map,args);
    }
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();

        // Check to make sure that types have not evolved incompatibly

        map.get("aaa");
        map.put("Aa","S");
    }
}
