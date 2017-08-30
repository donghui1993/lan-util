/**
 * 
 */
package cn.arefn.lan.util.list;

import java.util.List;
import java.util.ListIterator;

/**
 * @author donghui
 * @date 2017年7月10日
 */
public class Sorts
{
    /**
     * 合并排序数组 仿照排序规则，采用int类型比较值，对大于0的内容进行排序。
     * 
     * @param array
     *            排序数组内容
     * @param first
     *            第一个元素索引
     * @param mid
     *            元素中间位置索引
     * @param last
     *            元素最后一个位置索引
     * @param temp
     *            临时占用数组
     * @param sortMember
     *            排序规则
     */
    static <T> void mergearray(Object[] array, int first, int mid, int last, Object[] temp, SortMember<T> sortMember)
    {
        int i = first, j = mid + 1;
        int m = mid, n = last;
        int k = 0;
        
        while (i <= m && j <= n)
        {
            if (sortMember.compare((T) array[i], (T) array[j]) > 0)
            {
                temp[k++] = array[i++];
            }
            else
            {
                temp[k++] = array[j++];
            }
        }
        while (i <= m)
            temp[k++] = array[i++];
        
        while (j <= n)
            temp[k++] = array[j++];
        
        for (i = 0; i < k; i++)
            array[first + i] = temp[i];
    }
    
    /**
     * 归并排序
     * 
     * @param array
     *            排序数组内容
     * @param first
     *            第一个元素
     * @param last
     *            最后一个元素
     * @param temp
     *            临时占用数组
     * @param sortMember
     *            排序规则
     */
    static <T> void sort(Object[] array, int first, int last, Object[] temp, SortMember<T> sortMember)
    {
        if (first < last)
        {
            int mid = (first + last) / 2;
            sort(array, first, mid, temp, sortMember); // 左边有序
            sort(array, mid + 1, last, temp, sortMember); // 右边有序
            mergearray(array, first, mid, last, temp, sortMember); // 再将二个有序数列合并
        }
    }
    
    /**
     * 按照给定规则排序
     * 
     * @param list
     *            范型集合
     * @param sortMember
     *            排序规则
     */
    static <T> void sort(List<T> list, SortMember<T> sortMember)
    {
        Object[] array = list.toArray();
        sort(array, 0, array.length - 1, new Object[array.length], sortMember);
        ListIterator<T> it = list.listIterator();
        for (Object object : array)
        {
            it.next();
            it.set((T) object);
        }
    }
}
