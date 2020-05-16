package per.lagomoro.rocktime.ui.widget;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import per.lagomoro.rocktime.controller.GraphicsController;
import per.lagomoro.rocktime.ui.module.Controllable;

@SuppressWarnings("serial")
public class Button extends JButton implements Controllable{
	
	protected int clickTime = 0;
	protected boolean dragAppend = false;
	protected MouseEvent event;
	
	public Button(){
		super();
		this.initialize();
		this.create();
		this.refresh();
		
	}

	protected void initialize() {
		this.setOpaque(false);
		this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setBorder(null);
        this.setContentAreaFilled(false);
	}
	
	protected void create() {
		this.createListener();
	}

	/**
	 * Create event listeners.
	 **/
	protected void createListener() {
		MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e)  {onMouseEntered(e); }
            @Override
            public void mouseExited(MouseEvent e)   {onMouseExited(e);  }
            @Override
            public void mousePressed(MouseEvent e)  {
				event = e;
				onMousePressed(e);
			}
            @Override
            public void mouseReleased(MouseEvent e) {
            	dragAppend = false;
            	onMouseReleased(e);
                if(getModel().isRollover()) {
                	onMouseReleasedInside(e);
                } else {
                	onMouseReleasedOutside(e);
	            }
            }
			@Override
            public void mouseClicked(MouseEvent e) {
				switch(e.getButton()) {
				case MouseEvent.BUTTON1 : {
					onMouseClicked(e);
					break;
				}
				case MouseEvent.BUTTON3 :{
					onMouseRightClicked(e);
					break;
				}
				}
            }
			@Override
            public void mouseMoved(MouseEvent e)  {
				onMouseMoved(e);
			}
			@Override
            public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}
        };
		this.addMouseListener(adapter);
		this.addMouseMotionListener(adapter);
	}

	protected void onMouseEntered(MouseEvent e) {}	
	protected void onMouseExited(MouseEvent e) {}
	
	protected void onMousePressed(MouseEvent e) {}
	protected void onMouseLongPressed(MouseEvent e) {}
	
	protected void onMouseClicked(MouseEvent e) {}
	protected void onMouseRightClicked(MouseEvent e) {}
	
	protected void onMouseReleased(MouseEvent e) {}
	protected void onMouseReleasedInside(MouseEvent e) {}
	protected void onMouseReleasedOutside(MouseEvent e) {}
	
	protected void onMouseMoved(MouseEvent e) {}
	protected void onMouseDragged(MouseEvent e) {}
	protected void onMouseMovedLot(MouseEvent e) {}
	protected void onMouseDraggedLot(MouseEvent e) {}

	/**
	 * Update interface.
	 **/
	public void update() {
		if(getModel().isPressed() && getModel().isRollover()) {
			this.clickTime ++;
		}else{
			this.clickTime = 0;
		}
	}
	
	public void refresh() {
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Show drawing interface.
	 **/
	@Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		GraphicsController.setHint(graphics);
		this.paint(graphics);
        super.paintComponent(g);
    }
	
	protected void paint(Graphics2D graphics) {
		
	}

	
}
