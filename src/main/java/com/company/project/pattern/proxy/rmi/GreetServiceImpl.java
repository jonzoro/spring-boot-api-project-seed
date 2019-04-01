package com.company.project.pattern.proxy.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GreetServiceImpl extends UnicastRemoteObject implements GreetService {
    private static final long serialVersionUID = 3434060152387200042L;

    public GreetServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello " + name;
    }
}
