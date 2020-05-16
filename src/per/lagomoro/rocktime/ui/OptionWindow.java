package per.lagomoro.rocktime.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import per.lagomoro.rocktime.controller.MusicController;
import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.ui.module.Controllable;
import per.lagomoro.rocktime.ui.module.KeyAdapterSet;
import per.lagomoro.rocktime.ui.widget.ButtonStyled;
import per.lagomoro.rocktime.controller.GraphicsController;

@SuppressWarnings("serial")
public class OptionWindow extends JFrame{
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 370;
	public static final String TITLE = "游戏设置";
	
	public OptionWindow(){
		super();
		this.initialize();
		this.create();
		this.refresh();
	}

	protected void initialize(){
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT + 35);
		
		this.addKeyListener(new KeyAdapterSet());
	}
	
	protected void create() {
		int placeX = WIDTH - 50;
		ButtonStyled closeButton = new ButtonStyled(GraphicsController.WARNING_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				deactive();
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				graphics.setColor(Color.BLACK);
				graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				graphics.drawLine(17 + 1, 10 + 1, 32 - 1, 25 - 1);
				graphics.drawLine(17 + 1, 25 - 1, 32 - 1, 10 + 1);
			}
		};
		closeButton.setBounds(placeX, 0, 50, 35);
		closeButton.addKeyListener(new KeyAdapterSet());
		this.add(closeButton);
		
		
		placeX = WIDTH / 2;
		int placeY = 148;
		int place = 40;
		int padding = 50;
		int width = 40;
		int height = 35;
		ButtonStyled addSpeedButton = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setSpeed(0.1);
			}
		};
		addSpeedButton.setBounds(placeX + place, placeY, width, height);
		addSpeedButton.setBorder(BorderFactory.createEmptyBorder());
		addSpeedButton.setHorizontalAlignment(SwingConstants.CENTER);
		addSpeedButton.addKeyListener(new KeyAdapterSet());
		addSpeedButton.setText("+");
		this.add(addSpeedButton);
		
		ButtonStyled minusSpeedButton = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setSpeed(-0.1);
			}
		};
		minusSpeedButton.setBounds(placeX - place - width, placeY, width, height);
		minusSpeedButton.setBorder(BorderFactory.createEmptyBorder());
		minusSpeedButton.setHorizontalAlignment(SwingConstants.CENTER);
		minusSpeedButton.addKeyListener(new KeyAdapterSet());
		minusSpeedButton.setText("-");
		this.add(minusSpeedButton);
		
		ButtonStyled addSpeed2Button = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setSpeed(1);
			}
		};
		addSpeed2Button.setBounds(placeX + place + padding, placeY, width, height);
		addSpeed2Button.setBorder(BorderFactory.createEmptyBorder());
		addSpeed2Button.setHorizontalAlignment(SwingConstants.CENTER);
		addSpeed2Button.addKeyListener(new KeyAdapterSet());
		addSpeed2Button.setText(">");
		this.add(addSpeed2Button);
		
		ButtonStyled minusSpeed2Button = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setSpeed(-1);
			}
		};
		minusSpeed2Button.setBounds(placeX - place - padding - width, placeY, width, height);
		minusSpeed2Button.setBorder(BorderFactory.createEmptyBorder());
		minusSpeed2Button.setHorizontalAlignment(SwingConstants.CENTER);
		minusSpeed2Button.addKeyListener(new KeyAdapterSet());
		minusSpeed2Button.setText("<");
		this.add(minusSpeed2Button);
		
		placeY = 288;
		ButtonStyled addDelayButton = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setDelay(0.1);
			}
		};
		addDelayButton.setBounds(placeX + place, placeY, width, height);
		addDelayButton.setBorder(BorderFactory.createEmptyBorder());
		addDelayButton.setHorizontalAlignment(SwingConstants.CENTER);
		addDelayButton.addKeyListener(new KeyAdapterSet());
		addDelayButton.setText("+");
		this.add(addDelayButton);
		
		ButtonStyled minusDelayButton = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setDelay(-0.1);
			}
		};
		minusDelayButton.setBounds(placeX - place - width, placeY, width, height);
		minusDelayButton.setBorder(BorderFactory.createEmptyBorder());
		minusDelayButton.setHorizontalAlignment(SwingConstants.CENTER);
		minusDelayButton.addKeyListener(new KeyAdapterSet());
		minusDelayButton.setText("-");
		this.add(minusDelayButton);
		
		ButtonStyled addDelay2Button = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setDelay(1);
			}
		};
		addDelay2Button.setBounds(placeX + place + padding, placeY, width, height);
		addDelay2Button.setBorder(BorderFactory.createEmptyBorder());
		addDelay2Button.setHorizontalAlignment(SwingConstants.CENTER);
		addDelay2Button.addKeyListener(new KeyAdapterSet());
		addDelay2Button.setText(">");
		this.add(addDelay2Button);
		
		ButtonStyled minusDelay2Button = new ButtonStyled(GraphicsController.HOVER_COLOR, GraphicsController.DEFAULT_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				NodeController.setDelay(-1);
			}
		};
		minusDelay2Button.setBounds(placeX - place - padding - width, placeY, width, height);
		minusDelay2Button.setBorder(BorderFactory.createEmptyBorder());
		minusDelay2Button.setHorizontalAlignment(SwingConstants.CENTER);
		minusDelay2Button.addKeyListener(new KeyAdapterSet());
		minusDelay2Button.setText("<");
		this.add(minusDelay2Button);
		
		ViewOption viewOption = new ViewOption();
		viewOption.setBounds(0, 35, WIDTH, HEIGHT);
		viewOption.addKeyListener(new KeyAdapterSet());
		this.add(viewOption);
		
		ButtonStyled moveView = new ButtonStyled() {
			int startX, startY;
			
			@Override
			protected void onMousePressed(MouseEvent e) {
				super.onMousePressed(e);
				startX = e.getXOnScreen();
                startY = e.getYOnScreen();
			}
			
	        @Override
	        protected void onMouseDragged(MouseEvent e) {
	        	super.onMouseDragged(e);
	        	int deltaX = e.getXOnScreen() - startX;
	        	int deltaY = e.getYOnScreen() - startY;
	        	Point place = OptionWindow.this.getLocationOnScreen();
	        	OptionWindow.this.setLocation(place.x + deltaX, place.y + deltaY);
	        	startX = e.getXOnScreen();
                startY = e.getYOnScreen();
	        }
	        
	        @Override
	        public void paint(Graphics2D graphics) {
	        	super.paint(graphics);
	        	if(NodeController.getFever()) {
		    		graphics.setColor(MusicController.getPlayColor());
		    		graphics.fillRoundRect(this.shade, this.shade, this.getWidth() - this.shade * 2, this.getHeight() - this.shade * 2, this.radius * 2, this.radius * 2);
	        	}
	        }
	        
	    	@Override
	    	public void update() {
	    		super.update();
	    		this.refresh();
	    	}
	        
		};
		moveView.setBounds(0, 0, WIDTH, 35);
		moveView.addKeyListener(new KeyAdapterSet());
		moveView.setText(TITLE);
		this.add(moveView);
	}

	/**
	 * Update interface.
	 **/
	public void update() {
		for (Component c: this.getContentPane().getComponents()) {
			Controllable.updateChild(c);
		}
	}

	public void refresh() {
		this.revalidate();
		this.repaint();
		for (Component c: this.getContentPane().getComponents()) {
			Controllable.refreshChild(c);
		}
	}
    
	public void active() {
		this.setVisible(true);
	}
	
	public void deactive() {
		this.setVisible(false);
	}
	
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setLocationRelativeTo(null);
    }

}
