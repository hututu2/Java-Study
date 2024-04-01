package org.example;
import com.alibaba.fastjson.JSON;
public class JsonTest {
    public static void main(String[] args) {
//        Person person = new Person("xiaoming",10);
//        String personJson = JSON.toJSONString(person);
//        System.out.println(personJson);

        String jsonPerson = "{\"@type\":\"org.example.Person\",\"age\":10,\"name\":\"xiaoming\"}";
        Object person = JSON.parseObject(jsonPerson);
        Object person1 = JSON.parse(jsonPerson);

//        System.out.println(person.getAge());
    }
}
