package test;

import org.junit.Test;

public class BuilderTest
{
    @Test

    public void test()
    {
        int a = 1;
        // Child build = Builder.create(new Child(), Child.class).set("age",
        // 10).build();
        System.out.println(++a + ++a);
    }
}
