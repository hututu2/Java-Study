package org.example;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.lang.Runtime;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.example.TestInvocationHandler;
public class proxyTest {
    public static void main(String[] args) throws Exception {
        Map map=new HashMap();
        InvocationHandler invocationHandler=new TestInvocationHandler(map);
        Map proxyMap=(Map) Proxy.newProxyInstance(Map.class.getClassLoader(),new Class[]{Map.class},invocationHandler);
        InvocationHandler o=new TestInvocationHandler(proxyMap);

        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(buffer);
        objectOutputStream.writeObject(o);
        String data = Base64.getEncoder().encodeToString(buffer.toByteArray());
        System.out.println(data);

        byte[] bytes=Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(b);
        input.readObject();
    }

}
