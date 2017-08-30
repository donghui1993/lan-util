package test.fakedata;

import java.util.List;
import java.util.Map;

public class B1
{
    private String name = "123123";
    int val = 99;
    List<String> list;
    Map<String, String> map;

    public B1()
    {
        // map.put("list", "1");
    }

    public B1 set(List<String> list)
    {
        this.list = list;
        return this;
    }
}
