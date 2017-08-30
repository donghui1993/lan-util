package test.fakedata;

import java.util.List;

public class T1
{
	String name;
	int val = 1;
	public List<String> list;

	@Override
	public String toString()
	{
		return "name=" + this.name + " ,val=" + this.val + ",list=" + list;
	}
}
