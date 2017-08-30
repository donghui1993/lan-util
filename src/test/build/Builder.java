package test.build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import test.fakedata.Father;

public class Builder<T>
{
    private Builder()
    {
    }

    private Father father;
    private T t;
    private Class<T> fatherClass;
    private static Builder<?> builder;

    @SuppressWarnings("unchecked")
    public static <T> Builder<T> create(Father father, Class<T> fatherClass)
    {
        if (builder == null)
        {
            builder = new Builder<T>();
        }
        builder.build(father, fatherClass);
        return (Builder<T>) builder;
    }

    @SuppressWarnings("unchecked")
    private void build(Father father, Class<?> fatherClass)
    {
        this.father = father;
        try
        {
            this.t = (T) fatherClass.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        this.fatherClass = (Class<T>) fatherClass;
    }

    public Builder<T> set(String moduleNm, Object instance)
    {
        try
        {
            Field field = fatherClass.getDeclaredField(moduleNm);
            field.setAccessible(true);
            field.set(t, instance);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return this;
    }

    public T build()
    {
        try
        {
            Method method = fatherClass.getSuperclass().getDeclaredMethod("getName");
            method.setAccessible(true);
            this.set("name", method.invoke(father));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }
}
