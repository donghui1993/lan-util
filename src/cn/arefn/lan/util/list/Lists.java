/**
 * 
 */
package cn.arefn.lan.util.list;

import java.util.ArrayList;
import java.util.List;

/**
 * list相关操作内容
 * 
 * @author donghui
 * @date 2017年6月15日
 */
public class Lists
{
    /**
     * 创建一个新的范型ArrayList
     * 
     * @param clazz
     *            范型类
     * @return 新的ArrayList
     */
    public static <T> List<T> newList(Class<T> clazz)
    {
        return new ArrayList<T>();
    }
    
    /**
     * 从数组转范型ArrayList
     * 
     * @param array
     *            类型数组
     * @return 范型ArrayList
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
     * 集合排序
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
