package per.lagomoro.rocktime.ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import per.lagomoro.rocktime.controller.MusicController;
import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.ui.module.Controllable;
import per.lagomoro.rocktime.controller.GraphicsController;

@SuppressWarnings("serial")
public class ViewOption extends JPanel implements Controllable{
	
	public static final int PADDING = 60;
	
	protected BufferedImage image;
	
	public ViewOption(){
		super();
		this.initialize();
		this.refresh();
	}
	
	protected void initialize() {
		this.setOpaque(false);
		this.setLayout(null);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	@Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		GraphicsController.setHint(graphics);
		//graphics.drawImage(image, 0, 0, this);
		this.paint(graphics);
        super.paintComponent(g);
    }
	
	public void paintBuffer() {
		if(image == null) {
 			image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
 		}
		Graphics2D graphics = (Graphics2D)image.getGraphics();
		GraphicsController.setHint(graphics);
		this.paint(graphics);
	}
	
	protected void paint(Graphics2D graphics) {
		this.paintBackground(graphics);
		
		this.paintOption(graphics);
		
		this.paintForeground(graphics);
	}
	
	protected void paintBackground(Graphics2D graphics) {
		graphics.setColor(GraphicsController.DEFAULT_COLOR);
		graphics.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);
	}
	
	protected void paintForeground(Graphics2D graphics) {
		graphics.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		graphics.setColor(NodeController.getFever() ? MusicController.getPlayColor() : new Color(230, 230, 230));
		graphics.drawRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	protected void paintOption(Graphics2D graphics) {
		graphics.setColor(GraphicsController.DARK_TOUCH_COLOR);
		
		int y = 80;
		String speedString = "速度调节";
		graphics.setFont(this.getDefaultFont(24));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		graphics.drawString(speedString, (this.getWidth() - fontMetrics.stringWidth(speedString))/2, y);
		
		y += 60;
		String speed = String.format("%.1f", NodeController.getDrawSpeed());
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 24));
		fontMetrics = graphics.getFontMetrics();
		graphics.drawString(speed, (this.getWidth() - fontMetrics.stringWidth(speed))/2, y);
		
		y += 80;
		String delayString = "判定调节";
		graphics.setFont(this.getDefaultFont(24));
		fontMetrics = graphics.getFontMetrics();
		graphics.drawString(delayString, (this.getWidth() - fontMetrics.stringWidth(delayString))/2, y);
	
		y += 60;
		String delay = String.format("%.0f", NodeController.getDrawDelay());
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 24));
		fontMetrics = graphics.getFontMetrics();
		graphics.drawString(delay, (this.getWidth() - fontMetrics.stringWidth(delay))/2, y);
		
		y = this.getHeight() - 4 - 10;
		String text = "轨道对应的按键是 D F J K";
		graphics.setFont(this.getDefaultFont(18));
		fontMetrics = graphics.getFontMetrics();
		graphics.drawString(text, (this.getWidth() - fontMetrics.stringWidth(text))/2, y);
	}
	
	@Override
	public void update() {
		this.paintBuffer();
		this.refresh();
	}
	
	public void refresh() {
		this.revalidate();
		this.repaint();
	}
	
	protected Font getDefaultFont(int size) {
		return new Font("Microsoft YaHei", Font.PLAIN, size);
	}
	
	protected Font getFont(String fontName, int size) {
		return new Font(fontName, Font.PLAIN, size);
	}

}
