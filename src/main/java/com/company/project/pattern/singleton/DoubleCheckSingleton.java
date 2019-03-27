package com.company.project.pattern.singleton;

/***
 * 双重检查加锁单例模式，可以减少同步的性能消耗。
 * 不适用于1.5之前的版本。
 */
public class DoubleCheckSingleton {

    private volatile static DoubleCheckSingleton doubleCheckSingleton;

    private DoubleCheckSingleton() {}

    public static DoubleCheckSingleton getInstance() {
        if (null == doubleCheckSingleton) {
            synchronized (DoubleCheckSingleton.class) {
                if (null == doubleCheckSingleton) {
                    doubleCheckSingleton = new DoubleCheckSingleton();
                }
            }
        }
        return doubleCheckSingleton;
    }
}
