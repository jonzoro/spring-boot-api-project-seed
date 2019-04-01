package com.company.project.pattern.proxy.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1098);
            Naming.bind("rmi://127.0.0.1:1098/GreetService", new GreetServiceImpl());
            System.out.println("Server started...");
        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
