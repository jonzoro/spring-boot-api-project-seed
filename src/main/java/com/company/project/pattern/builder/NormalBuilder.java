package com.company.project.pattern.builder;

/***
 * 实例在完全赋值后才会被创建并且后面不会被任意改变。
 */
public class NormalBuilder {

    // 手动定义属性变量
    private String name;

    private String sex;

    // 使用建造者重写构造函数（不希望被直接实例化所以应该私有化）
    private NormalBuilder(Builder builder) {
        this.name = builder.name;
        this.sex = builder.sex;
    }

    // 自动生成
    @Override
    public String toString() {
        return "NormalBuilder{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    // 手动添加建造者（因为调用的时候类还没被实例化所以是静态的）
    private static Builder builder() {
        return new Builder();
    }

    // 建造过程
    public static class Builder {

        private String name;

        private String sex;

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        NormalBuilder build() {
            return new NormalBuilder(this);
        }
    }

    public static void main(String[] args) {
        // 感受一下调用的区别

        NormalBuilder normalBuilder = NormalBuilder.builder().name("name").sex("sex").build();
        System.out.println(normalBuilder.toString());

        NormalBuilder normalBuilder1 = new NormalBuilder.Builder().name("name1").sex("sex1").build();
        System.out.println(normalBuilder1);
    }
}
