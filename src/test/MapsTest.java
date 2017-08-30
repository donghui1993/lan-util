package test;

import java.lang.reflect.Field;

import org.junit.Test;

import test.fakedata.Child;

public class MapsTest
{
    @Test
    public void test()
    {
        Field[] declaredFields = Child.class.getFields();
        for (Field field : declaredFields)
        {
            System.out.println(field.getName());
        }
    }
}
