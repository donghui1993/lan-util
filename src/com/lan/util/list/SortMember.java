/**
 * 
 */
package com.lan.util.list;

/**
 * @author donghui
 * @since 6.4.0
 * @date 2017��5��16��
 */
public interface SortMember<T>
{
    boolean result(T first, T second);
}
