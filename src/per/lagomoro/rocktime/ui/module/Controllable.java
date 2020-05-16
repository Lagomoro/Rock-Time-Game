package per.lagomoro.rocktime.ui.module;


import java.awt.Component;

public interface Controllable {
	
	public void update();
	
	static void updateChild(Component c) {
		if(c instanceof Controllable) {
			((Controllable) c).update();
		}
	}
	
	public void refresh();
	
	static void refreshChild(Component c) {
		if(c instanceof Controllable) {
			((Controllable) c).refresh();
		}
	}
}
