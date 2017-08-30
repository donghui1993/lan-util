/**
 * 
 */
package com.lan.util.image;

/**
 * @author donghui
 * @date 2017��5��15��
 */
public class ColorNode
{
    /**
     * �Ƿ�Ҷ�ӽڵ�
     */
    boolean isLeaf;
    /**
     * ��ɫ����ͳ��
     */
    int colorIndex;
    /**
     * ��������
     */
    int pixelCount;
    /**
     * ��ɫ��ɫ��ֵ
     */
    int r;

    /**
     * ��ɫ��ɫ��ֵ
     */
    int g;
    /**
     * ��ɫ��ɫ��ֵ
     */
    int b;
    
    /**
     * �ӽڵ㣬���Ϊ8
     */
    ColorNode[] children = new ColorNode[8];
    /**
     * s ָ����һ���ɼ򻯽ڵ�
     */
    ColorNode next;
}
