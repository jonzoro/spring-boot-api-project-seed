package com.company.project.pattern.proxy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GreetService extends Remote {
    String sayHello(String name) throws RemoteException;
}
