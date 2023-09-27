package org.example;

import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassesLoaderTest {
    public static void main(String[] args) throws Exception {

//URLClassLoader
//        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:///D:\\ctf_tools\\java_study\\tmp\\")});
//        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("http://127.0.0.1/")});
//        Class c = urlClassLoader.loadClass("Evil");
//        c.newInstance();
//
// ClassLoader.defineClass
//        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass",String.class, byte[].class, int.class, int.class);
//        defineClassMethod.setAccessible(true);
//        byte[] code = Files.readAllBytes(Paths.get("D:\\ctf_tools\\java_study\\tmp\\Evil.class"));
//        Class c = (Class) defineClassMethod.invoke(ClassLoader.getSystemClassLoader(),"Evil",code,0,code.length);
//        c.newInstance();

//Unsafe.defineClass
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(unsafeField);
        byte[] code = Files.readAllBytes(Paths.get("D://ctf_tools\\java_study\\tmp\\Evil.class"));
        Class c = unsafe.defineClass("Evil",code,0,code.length,ClassLoader.getSystemClassLoader(),null);
        c.newInstance();

    }
}
