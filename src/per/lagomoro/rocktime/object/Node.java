package per.lagomoro.rocktime.object;

import java.awt.Color;

public abstract class Node {
	
	public boolean delete = false;
	public Color color1, color2, color3;
	
	public abstract void update();
	public abstract void inputPress();
	public abstract void inputRelease();
	public abstract long getStartTime();
	public abstract long getFinishTime();
	
	public abstract DrawNode getDrawNode();
}
