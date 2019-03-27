package com.company.project.pattern.singleton;

/***
 * 经典的单例模式，在多线程的情况下会出现问题。
 */
class ClassicalSingleton {

    private static ClassicalSingleton classicalSingleton;

    private ClassicalSingleton() {
    }

    static ClassicalSingleton getInstance() {
        if (null == classicalSingleton) {
            classicalSingleton = new ClassicalSingleton();
        }
        return classicalSingleton;
    }
}
