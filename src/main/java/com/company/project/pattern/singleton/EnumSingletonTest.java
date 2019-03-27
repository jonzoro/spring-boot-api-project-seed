package com.company.project.pattern.singleton;

public class EnumSingletonTest {
    public static void main(String[] args) {
        Long l = EnumSingleton.INSTANCE.getInstance();
        System.out.println(l);
    }
}
