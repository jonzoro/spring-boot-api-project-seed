package com.company.project.pattern.singleton;

/***
 * 同步式单例模式，可以处理多线程但是效率较低。
 * 只有第一次执行的时候才需要同步，后面的每次调用都是累赘。
 */
public class SyncSingleton {

    private static SyncSingleton syncSingleton;

    private SyncSingleton() {}

    public static synchronized SyncSingleton getInstance() {
        if (null == syncSingleton) {
            syncSingleton = new SyncSingleton();
        }
        return syncSingleton;
    }
}
