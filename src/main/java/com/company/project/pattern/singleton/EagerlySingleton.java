package com.company.project.pattern.singleton;

/***
 * 饿汉式单例模式，JVM在加载类的时候就马上创建了单例，这是在任何线程访问之前的。
 * 可以应对多线程的情况。
 */
class EagerlySingleton {

    private static EagerlySingleton eagerlySingleton = new EagerlySingleton();

    private EagerlySingleton() {}

    public static EagerlySingleton getInstance() {
        return eagerlySingleton;
    }

}
