package com.company.project.core;

import com.company.project.annotation.CreateDate;
import com.company.project.annotation.CreateUser;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;

/***
 * 自动为固定字段赋值
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class MyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        // 获取SQL信息
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 获取所有字段
        Object parameter = invocation.getArgs()[1];
        List<Field> declaredFields = new ArrayList<>();
        Class tmpClass = parameter.getClass();
        do {
            declaredFields.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
            tmpClass = tmpClass.getSuperclass();
        } while (tmpClass != Object.class);

        // 处理固定值
        for (Field field : declaredFields) {
            if (null != field.getAnnotation(CreateUser.class)) {
                if (SqlCommandType.INSERT == sqlCommandType) {
                    field.setAccessible(true);
                    field.set(parameter, "admin");
                }
            }

            if (null != field.getAnnotation(CreateDate.class)) {
                if (SqlCommandType.INSERT == sqlCommandType) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}