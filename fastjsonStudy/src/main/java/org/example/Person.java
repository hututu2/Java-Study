package org.example;

import java.io.IOException;

public class Person {
    private String name;
    private int age;
    public Person(){
        System.out.println("无参构造方法");
    }
    public Person(String name,int age){
        System.out.println("有参构造方法");

        this.name = name;
        this.age = age;
    }

    public void setName(String name){
        System.out.println("setName");

        this.name = name;
    }
    public void setAge(int age){
        System.out.println("setAge");
        this.age = age;
    }
    public String getName(){
        System.out.println("getName");
        return this.name;
    }
    public int getAge() throws IOException {
        System.out.println("getAge");
        Runtime.getRuntime().exec("calc");
        return this.age;
    }
}
