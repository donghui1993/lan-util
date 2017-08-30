package test.fakedata;

public class Child extends Father
{
    int age = 1;
    String name;

    protected void setAge()
    {
        this.age = 11;
    }

    @Override
    public String toString()
    {
        return "Child [age=" + age + ", name=" + name + "]";
    }

}
