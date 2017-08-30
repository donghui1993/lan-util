/**
 * 
 */
package cn.arefn.lan.util.list;

/**
 * 定义排序规则的接口
 * 
 * @author donghui
 * @date 2017年6月15日
 */
public interface SortMember<T>
{
    /**
     * 对给定的值返回int值<br>
     * 正向排序 second - first > 0<br>
     * 逆向排序 first - second > 0
     * 
     * @param first
     *            前一个元素
     * @param second
     *            后一个元素
     * @return 比较结果
     */
    int compare(T first, T second);
}
