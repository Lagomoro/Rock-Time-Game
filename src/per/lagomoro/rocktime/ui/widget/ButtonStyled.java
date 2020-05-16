package per.lagomoro.rocktime.ui.widget;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import per.lagomoro.rocktime.controller.GraphicsController;

@SuppressWarnings("serial")
public class ButtonStyled extends Button{
	
	protected Color textColor;

	protected Color hoverColor = GraphicsController.EMPTY_COLOR;
	protected Color defaultColor = GraphicsController.EMPTY_COLOR;
	protected Color touchColor = GraphicsController.EMPTY_COLOR;
	
	protected int radius = 0;
	protected int shade = 0;
	
	protected boolean isWarning = false;
	protected boolean isProcessing = false;
	
	protected boolean locked = false;
	protected boolean active = true;
	
	protected int status = 0;
	
	public ButtonStyled(){
		super();
	}
	
	public ButtonStyled(Color hoverColor, Color defaultColor, Color touchColor){
		this.setColor(hoverColor, defaultColor, touchColor);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setColor(GraphicsController.EMPTY_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.EMPTY_COLOR);
		this.setTextColor(GraphicsController.DEFAULT_TEXT_COLOR);
		this.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		this.setHorizontalAlignment(JButton.LEFT);
		this.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 0));
		this.setBackground(this.defaultColor);
	}

	@Override
	protected void onMouseEntered(MouseEvent e) {
		super.onMouseEntered(e);
		this.setBackground(this.hoverColor);
	}	

	@Override
	protected void onMouseExited(MouseEvent e) {
		super.onMouseExited(e);
		this.setBackground(this.defaultColor);
	}
	
	@Override
	protected void onMousePressed(MouseEvent e) {
		this.setBackground(this.touchColor);
	}

	@Override
	protected void onMouseReleasedInside(MouseEvent e) {
		super.onMouseReleasedInside(e);
		this.setBackground(this.hoverColor);
	}
	
	/**
	 * Set color of button.
	 **/
	public void setColor(Color hoverColor, Color defaultColor, Color touchColor) {
		this.hoverColor = hoverColor;
		this.defaultColor = defaultColor;
		this.touchColor = touchColor;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
		this.setForeground(textColor);
	}
	
	/**
	 * Set radius of button.
	 **/
	public void setRadius(int radius) {
		this.radius = radius;
		this.resetBorder();
	}
	
	public void setShade(int shade) {
		int delta = shade - this.shade;
		this.setBounds(this.getX() - delta, this.getY() - delta, this.getWidth() + delta*2, this.getHeight() + delta * 2);
		this.shade = shade;
		this.resetBorder();
	}
	
	public void resetBorder() {
		int border = this.radius + this.shade;
		this.setBorder(BorderFactory.createEmptyBorder(0, border, 0, border));
	}
	
	@Override
	protected void paint(Graphics2D graphics) {
		super.paint(graphics);
		this.paintShade(graphics);
		this.paintBackground(graphics);
	}
	
	protected void paintShade(Graphics2D graphics) {
		int i = 0;
		if(this.shade != 0) {
			graphics.setColor(new Color(0, 0, 0, GraphicsController.SHADE_ALPHA / this.shade));
			for(i = 0;i < this.shade;i++) {
				graphics.fillRoundRect(i, i, this.getWidth() - i*2, this.getHeight() - i*2, (this.radius + this.shade - i) * 2, (this.radius + this.shade - i) * 2);
			}
		}
	}
	
	protected void paintBackground(Graphics2D graphics) {
		graphics.setColor(this.locked ? (this.active ? this.touchColor : this.defaultColor) : (this.active ? this.getBackground() : this.defaultColor));
		graphics.fillRoundRect(this.shade, this.shade, this.getWidth() - this.shade * 2, this.getHeight() - this.shade * 2, this.radius * 2, this.radius * 2);
	}

}
