/**
 * 
 */
package cn.arefn.lan.util.list;

import java.util.ArrayList;
import java.util.List;

/**
 * list��ز�������
 * 
 * @author donghui
 * @date 2017��6��15��
 */
public class Lists
{
    /**
     * ����һ���µķ���ArrayList
     * 
     * @param clazz
     *            ������
     * @return �µ�ArrayList
     */
    public static <T> List<T> newList(Class<T> clazz)
    {
        return new ArrayList<T>();
    }
    
    /**
     * ������ת����ArrayList
     * 
     * @param array
     *            ��������
     * @return ����ArrayList
     */
    public static <T> List<T> fromArray(T[] array)
    {
        int length = array.length;
        List<T> list = new ArrayList<T>(length);
        for (T t : array)
        {
            list.add(t);
        }
        return list;
    }
    
    /**
     * ��������
     * 
     * @param list
     * @param sortMember
     * @return
     */
    public static <T> boolean sort(List<T> list, SortMember<T> sortMember)
    {
        Sorts.sort(list, sortMember);
        return true;
    }
    
}
