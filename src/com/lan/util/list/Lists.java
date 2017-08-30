package com.lan.util.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Lists
{
    public static <T> List<T> sort(List<T> list, SortMember<T> sortMember)
    {
        
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
            for (int j = i + 1; j < size; j++)
            {
                T t = list.get(i);
                T t2 = list.get(j);
                if (sortMember.result(t, t2))
                {
                    list.set(i, t2);
                    list.set(j, t);
                }
            }
        }
        return list;
    }
    
    public static <T> List<T> newList(Class<T> clazz)
    {
        return new ArrayList<T>();
    }
	/**
	 * 在使用到可变参数时，需要将list里面的值转为数组内容。 list转数组
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list, Class<T> clazz)
	{
		int size = list.size();
		Object newInstance = Array.newInstance(clazz, size);
		T[] t = (T[]) newInstance;
		for (int i = 0; i < size; i++)
		{
			t[i] = list.get(i);
		}
		return t;
	}
}
