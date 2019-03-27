package com.company.project.pattern.singleton;

public enum EnumSingleton {

    INSTANCE;

    // 可定义任意类型
    private Long instance;

    EnumSingleton() {
        instance = 1000L;
    }

    public Long getInstance() {
        return instance;
    }
}
