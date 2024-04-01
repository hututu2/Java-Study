package org.example;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIRMIClient {
    public static void main(String[] args) throws NamingException {
        String url = "rmi://127.0.0.1:1099/Evil";
        InitialContext initialContext = new InitialContext();
        initialContext.lookup(url);
    }
}
