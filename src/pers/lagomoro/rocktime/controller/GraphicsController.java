package pers.lagomoro.rocktime.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsController {
	
	public static final int STROKE_INSIDE = -1;
	public static final int STROKE_MIDDLE = 0;
	public static final int STROKE_OUTSIDE = 1;
	
	public static final Color EMPTY_COLOR = new Color(0, 0, 0, 1);
	public static final Color DEFAULT_TINT_COLOR = Color.LIGHT_GRAY;
	
	public static final int SHADE_ALPHA = 40;
	
	public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	
	public static final Color WARNING_COLOR = Color.RED;
	public static final Color HOVER_COLOR = new Color(235, 235, 235, 255);
	public static final Color DEFAULT_COLOR = Color.WHITE;
	public static final Color TOUCH_COLOR = new Color(210, 210, 210, 255);

	public static final Color DARK_HOVER_COLOR = new Color(210, 210, 210, 255);
	public static final Color DARK_TOUCH_COLOR = new Color(170, 170, 170, 255);
	
	public static final Color HINT_BLUE_COLOR = new Color(50, 100, 180);
	public static final Color HINT_LIGHTBLUE_COLOR = new Color(0, 140, 230);

	public static final Color HOVER_BLUE_COLOR = new Color(60, 180, 240, 255);
	public static final Color DEFAULT_BLUE_COLOR = new Color(20, 160, 230, 255);
	public static final Color TOUCH_BLUE_COLOR = new Color(150, 150, 150, 255);
	
	public static void setHint(Graphics2D graphics) {
		//��ֵ��ʾ�� = ȡ����9��
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		//Alpha��ֵ��ʾ�� = Alpha����㷨���Ӿ���������
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        //�������ʾ�� = �����ģʽ�����
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//�ı��������ʾ�� = �����ģʽ��LCD��ʾ��������ˮƽ����RGBȡֵ
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //LCD �ı��Աȳ�����ʾ�� = �ı��Աȶȣ���
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 180);
		//����С�������ʾ�� = ��դ�������أ���
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
		//������ʾ�� = ͼ���������Ӿ���������
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		//��ɫ������ʾ�� = ��ɫת�����Ӿ���������
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        //������ʾ�� = ɫ�ʶ���������
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        //�ʻ��淶��������ʾ�� = ������״����Ⱦ��������
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}
	
}
