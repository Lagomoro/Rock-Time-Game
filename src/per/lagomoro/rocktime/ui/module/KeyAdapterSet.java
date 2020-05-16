package per.lagomoro.rocktime.ui.module;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import per.lagomoro.rocktime.controller.NodeController;

public class KeyAdapterSet extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		NodeController.inputPress(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		NodeController.inputRelease(e.getKeyCode());
	}

}
