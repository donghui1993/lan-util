/**
 * 
 */
package cn.arefn.lan.util.list;

/**
 * �����������Ľӿ�
 * 
 * @author donghui
 * @date 2017��6��15��
 */
public interface SortMember<T>
{
    /**
     * �Ը�����ֵ����intֵ<br>
     * �������� second - first > 0<br>
     * �������� first - second > 0
     * 
     * @param first
     *            ǰһ��Ԫ��
     * @param second
     *            ��һ��Ԫ��
     * @return �ȽϽ��
     */
    int compare(T first, T second);
}
