package com.company.project.pattern.proxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        try {
            GreetService greetService = (GreetService) Naming.lookup("rmi://127.0.0.1:1098/GreetService");
            System.out.println(greetService.sayHello("Jobs"));
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
