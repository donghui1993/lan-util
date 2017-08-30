/**
 * 
 */
package com.lan.util.image;

/**
 * @author donghui
 * @date 2017年5月15日
 */
public class ColorNode
{
    /**
     * 是否叶子节点
     */
    boolean isLeaf;
    /**
     * 颜色总量统计
     */
    int colorIndex;
    /**
     * 像素总数
     */
    int pixelCount;
    /**
     * 红色颜色总值
     */
    int r;

    /**
     * 绿色颜色总值
     */
    int g;
    /**
     * 蓝色颜色总值
     */
    int b;
    
    /**
     * 子节点，广度为8
     */
    ColorNode[] children = new ColorNode[8];
    /**
     * s 指向下一个可简化节点
     */
    ColorNode next;
}
