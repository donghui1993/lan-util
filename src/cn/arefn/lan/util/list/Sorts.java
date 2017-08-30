/**
 * 
 */
package cn.arefn.lan.util.list;

import java.util.List;
import java.util.ListIterator;

/**
 * @author donghui
 * @date 2017��7��10��
 */
public class Sorts
{
    /**
     * �ϲ��������� ����������򣬲���int���ͱȽ�ֵ���Դ���0�����ݽ�������
     * 
     * @param array
     *            ������������
     * @param first
     *            ��һ��Ԫ������
     * @param mid
     *            Ԫ���м�λ������
     * @param last
     *            Ԫ�����һ��λ������
     * @param temp
     *            ��ʱռ������
     * @param sortMember
     *            �������
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
     * �鲢����
     * 
     * @param array
     *            ������������
     * @param first
     *            ��һ��Ԫ��
     * @param last
     *            ���һ��Ԫ��
     * @param temp
     *            ��ʱռ������
     * @param sortMember
     *            �������
     */
    static <T> void sort(Object[] array, int first, int last, Object[] temp, SortMember<T> sortMember)
    {
        if (first < last)
        {
            int mid = (first + last) / 2;
            sort(array, first, mid, temp, sortMember); // �������
            sort(array, mid + 1, last, temp, sortMember); // �ұ�����
            mergearray(array, first, mid, last, temp, sortMember); // �ٽ������������кϲ�
        }
    }
    
    /**
     * ���ո�����������
     * 
     * @param list
     *            ���ͼ���
     * @param sortMember
     *            �������
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
