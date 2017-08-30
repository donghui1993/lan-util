package test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.lan.util.object.Objects;
import com.lan.util.string.Parsers;

import test.fakedata.B1;
import test.fakedata.T1;

public class SomeTest
{
	private String tablenm = "MyTable";
	private int aget = 1;
	private int agets = 1;
	private String tablenms = "MyTable";

	public String getTablenm()
	{
		return tablenm;
	}

	public void setTablenm(String tablenm)
	{
		this.tablenm = tablenm;
	}

	public int getAget()
	{
		return aget;
	}

	public void setAget(int aget)
	{
		this.aget = aget;
	}

	public int getAgets()
	{
		return agets;
	}

	public void setAgets(int agets)
	{
		this.agets = agets;
	}

	public String getTablenms()
	{
		return tablenms;
	}

	public void setTablenms(String tablenms)
	{
		this.tablenms = tablenms;
	}

	@Test
	public void testString()
	{
		int i = 1000 * 10000;
		long date = new Date().getTime();
		while (i-- > 0)
		{
			List<Object> list = new ArrayList<>();
			list.add(new SomeTest());
			list.add("aaa");
			list.add("bbb");
			Parsers.paserList("select * from {$tablenm} {$str}  {$strb}", list);
		}
		long dateE = new Date().getTime();
		System.out.println("time rase = " + (dateE - date));
	}

	@Test
	public void testMix()
	{
		List arrayList = new ArrayList();
		arrayList.add(1);
		arrayList.add(1);
		arrayList.add(1);
		arrayList.add(1);
		T1 mixin = Objects.mixin(new T1(), new B1().set(arrayList));
		System.out.println(mixin);
	}

	@Test
	public void testS()
	{
		int i = 1000 * 10000;
		long date = new Date().getTime();

		while (i-- > 0)
		{
			Objects.mixin(new T1(), new B1());
			Objects.mixin(new B1(), new T1());
			// Maps.method2Map(new SomeTest());
			// Maps.field2Map(new SomeTest());
		}
		long dateE = new Date().getTime();
		System.out.println("time rase = " + (dateE - date));
	}

	@Test
	public void testM()
	{
		System.out.println('a' - 'A');
	}

	@Test
	public void testc()
	{
		int[] arr = { 2, 5, 7, 1, 8 };
		int[] b = { 1, 2, 5, 7, 8 };

		for (int i = 0; i < arr.length; i++)
		{
			int k = arr[i];
			for (int j = i; j < b.length; j++)
			{
				int l = b[j];
				if (k == l)
				{
					int m = b[i];
					b[i] = l;
					b[j] = m;
				}
			}
		}
		for (int i : b)
		{
			System.out.println(i);
		}
	}

	@Test
	public void testTpName()
	{
		Object ob = 1;
		System.out.println(ob.getClass().getName());
	}

	@Test
	public void testSameType()
	{
		Field field = new T1().getClass().getFields()[0];
		ArrayList<String> arrayList = new ArrayList<String>();
		cout(Objects.sameType(field, arrayList));

	}

	@Test
	public void isbasic()
	{
        Object obj = new String[] {};
        cout(obj.getClass().isArray());
	}

	public void cout(Object... objects)
	{
		for (Object object : objects)
		{
			System.out.println(object);
		}
	}
}
