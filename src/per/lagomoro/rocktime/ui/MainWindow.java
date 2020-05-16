package per.lagomoro.rocktime.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import per.lagomoro.rocktime.controller.MusicController;
import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.ui.module.Controllable;
import per.lagomoro.rocktime.ui.module.KeyAdapterSet;
import per.lagomoro.rocktime.ui.widget.ButtonStyled;
import per.lagomoro.rocktime.controller.GraphicsController;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	
	public static final int WIDTH = 812 / 2;
	public static final int HEIGHT = 624 + 100;
	public static final String TITLE = "Rock Time";
	
	public ButtonStyled pauseButton;
	
	public OptionWindow optionWindow;
	
	public MainWindow(){
		super();
		this.initialize();
		this.create();
		this.refresh();
	}

	protected void initialize(){
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT + 35);
		
		this.addKeyListener(new KeyAdapterSet());
		this.requestFocus();
		
		this.optionWindow = new OptionWindow();
	}
	
	protected void create() {
		ViewMusic viewMusic = new ViewMusic();
		viewMusic.setBounds(0, 35, WIDTH, HEIGHT);
		viewMusic.addKeyListener(new KeyAdapterSet());
		this.add(viewMusic);
		
		int placeX = WIDTH - 200;
		this.pauseButton = new ButtonStyled(GraphicsController.DARK_HOVER_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				super.onMouseClicked(e);
				if(MusicController.getPlayStatus() > 1) {
					resume();
				}else if(MusicController.getPlayStatus() > 0) {
					pause();
				}
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				if(MusicController.getPlayStatus() > 1) {
					graphics.setColor(new Color(0, 150, 0));
					graphics.fillPolygon(new int[]{18, 18, 33}, new int[]{9, 25, 17}, 3);
				} else if(MusicController.getPlayStatus() > 0) {
					graphics.setColor(new Color(150, 0, 0));
					graphics.fillRect(17, 10, 5, 15);
					graphics.fillRect(27, 10, 5, 15);
				}
			}
		};
		this.pauseButton.setBounds(placeX, 0, 50, 35);
		this.pauseButton.addKeyListener(new KeyAdapterSet());
		this.add(this.pauseButton);
		
		placeX += 50;
		ButtonStyled playButton = new ButtonStyled(GraphicsController.DARK_HOVER_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				super.onMouseClicked(e);
				if(MusicController.getPlayStatus() > 0) {
					stop();
				}else {
					play();
				}
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				if(MusicController.getPlayStatus() > 0) {
					graphics.setColor(new Color(150, 0, 0));
					graphics.fillRect(17, 10, 15, 15);
				}else {
					graphics.setColor(new Color(0, 150, 0));
					graphics.fillPolygon(new int[]{18, 18, 33}, new int[]{9, 25, 17}, 3);
				}
			}
		};
		playButton.setBounds(placeX, 0, 50, 35);
		playButton.addKeyListener(new KeyAdapterSet());
		this.add(playButton);
		
		placeX += 50;
		ButtonStyled optionButton = new ButtonStyled(GraphicsController.DARK_HOVER_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				optionWindow.active();
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				graphics.setColor(Color.BLACK);
				graphics.setColor(Color.BLACK);
				int ox = 25, oy = 18, radius = 3, r1 = 6, r2 = 9;
				this.drawArc(graphics, ox, oy, radius, 0, 360, 1, 0, Color.BLACK);
				for(int i = 0; i < 12;i++) {
					double angle = Math.PI * 2 / 12;
					double startAngle = angle * 0;
					int x1 = (int)(r1 * Math.cos(startAngle + angle * i)) + ox;
					int x2 = (int)(r2 * Math.cos(startAngle + angle * i)) + ox;
					int y1 = (int)(r2 * Math.sin(startAngle + angle * i)) + oy;
					int y2 = (int)(r2 * Math.sin(startAngle + angle * i)) + oy;
					graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
					graphics.drawLine(x1, y1, x2, y2);
					this.drawArc(graphics, ox, oy, i % 2 == 0 ? r1 : r2, (int)((startAngle + angle * i) * 180 / Math.PI), (int)((startAngle + angle) * 180 / Math.PI), 1, 0, Color.BLACK);
				}
			}
			public static final int STROKE_INSIDE = -1;
			@SuppressWarnings("unused")
			public static final int STROKE_MIDDLE = 0;
			public static final int STROKE_OUTSIDE = 1;
			public void drawArc(Graphics2D graphics, int ox, int oy, int radius, int startAngle, int arcAngle, int lineWidth, int drawMode, Color color) {
				graphics.setColor(color);
				graphics.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				int sp = 0 - radius - (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth/2);
				int sd = radius * 2 + (Math.max(STROKE_INSIDE, Math.min(STROKE_OUTSIDE, drawMode)) * lineWidth);
				graphics.drawArc(ox + sp, oy + sp, sd, sd, startAngle, arcAngle);
			}
		};
		optionButton.setBounds(placeX, 0, 50, 35);
		optionButton.addKeyListener(new KeyAdapterSet());
		this.add(optionButton);
		
		placeX += 50;
		ButtonStyled closeButton = new ButtonStyled(GraphicsController.WARNING_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				System.exit(0);
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
	        	Point place = MainWindow.this.getLocationOnScreen();
	        	MainWindow.this.setLocation(place.x + deltaX, place.y + deltaY);
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
		this.pauseButton.setVisible(MusicController.getPlayStatus() > 0);
		this.optionWindow.update();
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
    
    public void play(){};
    public void pause(){};
    public void resume(){};
    public void stop(){};

}
