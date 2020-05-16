package per.lagomoro.rocktime.object;

import java.awt.Color;

public class DrawNode {

	public int startY;
	public int endY;
	
	public Color color1;
	public Color color2;
	public Color color3;
	
	public boolean drawHead = false;
	public boolean drawLine = false;
	
	public DrawNode(int startY, int endY, Color color1, Color color2, Color color3, boolean drawHead, boolean drawLine) {
		this.startY = startY;
		this.endY = endY;
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.drawHead = drawHead;
		this.drawLine = drawLine;
	}

}
