package per.lagomoro.rocktime.ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import per.lagomoro.rocktime.controller.MusicController;
import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.object.DrawNode;
import per.lagomoro.rocktime.object.Hard;
import per.lagomoro.rocktime.ui.module.Controllable;
import per.lagomoro.rocktime.controller.GraphicsController;

@SuppressWarnings("serial")
public class ViewMusic extends JPanel implements Controllable{
	
	public static final int PADDING = 60;
	public static final int LINE_PLACE = 210;
	
	protected BufferedImage image;
	
	public ViewMusic(){
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
		
		if(MusicController.getPlayStatus() > 0) {
			this.paintFFT(graphics);
		}
		
		if(MusicController.getNowMusic() != "") {
			this.paintProcess(graphics, -140);
			this.paintHard(graphics, -140);
		}
		
		int railCount = NodeController.getRailCount();
		this.paintRail(graphics, railCount);
		
		if(MusicController.getPlayStatus() > 0) {
			this.paintJudge(graphics);
			this.paintFever(graphics);
		}
		
		this.paintRailPress(graphics, railCount);
		this.paintRailNodes(graphics, railCount);
		
		if(MusicController.getPlayStatus() == 0 && MusicController.getCurrentTime() > 0) {
			this.paintFinal(graphics);
		}
		
		if(MusicController.getPlayStatus() > 0 || MusicController.getWaitTime() > 0 && MusicController.getWaitTime() != 5000) {
			this.paintScore(graphics);
		}
		
		this.paintWait(graphics);
		
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
	
	protected void paintWait(Graphics2D graphics) {
		long waitTime = MusicController.getWaitTime();
		int y = 2 + 200;
		if(waitTime > 0 && waitTime != 5000){
			long time = Math.max(waitTime - 1000, 0)/1000;
			String waitString = time == 0 ? "Start!" : Long.toString(time);
			graphics.setColor(GraphicsController.DARK_TOUCH_COLOR);
			graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 60));
			FontMetrics fontMetrics = graphics.getFontMetrics();
			graphics.drawString(waitString, (this.getWidth() - fontMetrics.stringWidth(waitString))/2, y);
		}
	}
	
	protected void paintFFT(Graphics2D graphics) {
		double[] drawInstance = MusicController.getDrawInstance();
		double[] dropInstance = MusicController.getDropInstance();
		graphics.setColor(GraphicsController.DARK_HOVER_COLOR);
		int width = 8;
		int y = this.getHeight() - 2;
		for(int i = 0; i < drawInstance.length; i++) {
			int nowX = 2 + (i + 1) * width;
			int realX = this.getWidth() - nowX;
			if(realX + width - 1 < 0) continue;
			int height = Math.min((int)drawInstance[i], this.getHeight() - 5);
			graphics.fillRect(realX, y - height, width - 1, height);
			graphics.fillRect(realX, y - (int)dropInstance[i] - 5, width - 1, 4);
		}
	}
	
	protected void paintHard(Graphics2D graphics, int placeY) {
		int y = this.getHeight() - 2 + placeY - 31;
		
		Hard hard = NodeController.getHard();
		graphics.setColor(hard.color);
		graphics.fillRoundRect((this.getWidth() - 50) / 2, y - 12, 50, 15, 5, 5);
		
		String hardString = hard.name;
		graphics.setColor(Color.WHITE);
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 12));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		graphics.drawString(hardString, (this.getWidth() - fontMetrics.stringWidth(hardString))/2, y);
	}
	
	protected void paintProcess(Graphics2D graphics, int placeY) {
		int y = this.getHeight() - 2 + placeY;
		
		graphics.setColor(GraphicsController.DARK_HOVER_COLOR);
		graphics.setFont(this.getDefaultFont(18));
		String musicName = MusicController.getNowMusic();
		int textWidth = (int)(graphics.getFont().getStringBounds(musicName, new FontRenderContext(new AffineTransform(), true, true)).getWidth());
		
		graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		graphics.drawRect(PADDING - 2, y + 20 - 2, this.getWidth() - PADDING * 2 + 4, 10 + 4);
		graphics.fillRect(PADDING, y + 20, (int)((this.getWidth() - PADDING * 2) * MusicController.getProcess()), 10);

		graphics.setColor(Color.LIGHT_GRAY);
		graphics.drawString(musicName, (this.getWidth() - textWidth)/2, y);
		
		graphics.setFont(this.getDefaultFont(16));
		long time = MusicController.getDrawTime();
		String currentTime = this.getTimeString(time > 0 ? time : 0);
		textWidth = (int)(graphics.getFont().getStringBounds(currentTime, new FontRenderContext(new AffineTransform(), true, true)).getWidth());
		graphics.drawString(currentTime, PADDING - 10 - textWidth, y + 31);
		graphics.drawString(this.getTimeString(MusicController.getMaxTime()), this.getWidth() - PADDING + 10, y + 31);
	}
	
	protected void paintJudge(Graphics2D graphics) {
		Image image = NodeController.getLastJudgeImage();
		int y = this.getHeight() - 100 - LINE_PLACE - 2 - NodeController.getJudgeY();
		int combo = NodeController.getCombo();
		if(image != null) {
			graphics.drawImage(image, (this.getWidth() - 200) / 2, y, this);
		}
		if(combo > 1) {
			String comboString = Integer.toString(combo);
			graphics.setColor(GraphicsController.DARK_TOUCH_COLOR);
			graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 24));
			FontMetrics fontMetrics = graphics.getFontMetrics();
			graphics.drawString(comboString, (this.getWidth() - fontMetrics.stringWidth(comboString))/2, y + 65);
		}
	}
	
	protected void paintFever(Graphics2D graphics) {
		Image image = new ImageIcon("./assets/images/fever.png").getImage();
		int y = 2 + 100 ;
		int feverY = Math.max(NodeController.getFeverY(), 0);
		if(NodeController.getFever()) {
			graphics.drawImage(image, (this.getWidth() - 300) / 2, y - feverY, this);
		}
	}
	
	protected void paintRail(Graphics2D graphics, int railCount) {
		graphics.setColor(new Color(230, 230, 230));
		int width = this.getWidth() - 4;
		int height = this.getHeight() - 4;
		for (int i = 1; i < railCount; i++) {
			graphics.fillRect(2 + (int)((width / railCount) * i) - 1, 2, 2, height - LINE_PLACE);
		}
		graphics.setColor(GraphicsController.DARK_HOVER_COLOR);
		graphics.fillRect(2, 2 + height - LINE_PLACE, width, 1);
	}
	
	protected void paintRailPress(Graphics2D graphics, int railCount) {
		graphics.setColor(GraphicsController.DARK_HOVER_COLOR);
		int width = this.getWidth() - 4;
		int height = this.getHeight() - 4;
		Boolean[] railPressed = NodeController.getRailPressed();
		for (int i = 0; i < railCount; i++) {
			if(!railPressed[i]) continue;
			graphics.fillRect(2 + (int)((width / railCount) * i), 2 + height - 5 - LINE_PLACE, (width / railCount), 10);
		}
	}
	
	protected void paintRailNodes(Graphics2D graphics, int railCount) {
		ArrayList<ArrayList<DrawNode>> nodeList = NodeController.getRailNodes();
		int width = this.getWidth() - 4;
		int height = this.getHeight() - 4 - LINE_PLACE;
		int x, y, y1, y2;
		for (int i = 0; i < railCount; i++) {
			ArrayList<DrawNode> nodes = nodeList.get(i);
			for(DrawNode node : nodes) {
				x = 2 + (int)((width / railCount) * ((double)i + 0.5));
				if(node.drawLine) {
					y1 = 2 + height - node.startY;
					y2 = 2 + height - (node.endY < 0 ? 0 : node.endY);
					graphics.setColor(node.color1);
					graphics.fillPolygon(new int[]{x - 10, x - 10, x, x + 10, x + 10, x}, new int[]{y1, y2, y2 - 10, y2, y1, y1 + 10}, 6);
				}
				if(node.drawHead) {
					y = 2 + height - node.startY;
					graphics.setColor(node.color3);
					graphics.fillPolygon(new int[]{x - 10, x, x + 10, x}, new int[]{y, y + 10, y, y - 10}, 4);
					graphics.setColor(node.color2);
					graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
					graphics.drawPolygon(new int[]{x - 15, x, x + 15, x}, new int[]{y, y + 15, y, y - 15}, 4);
				}
			}
		}
	}
	
	protected void paintScore(Graphics2D graphics) {
		int score = NodeController.getScore();
		graphics.setColor(GraphicsController.DARK_TOUCH_COLOR);
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 20));
		FontMetrics fontMetrics = graphics.getFontMetrics();
		String scoreString = Integer.toString(score);
		graphics.drawString(scoreString, 15, 40);
		
		int time = NodeController.getScoreTime();
		int lastScore = NodeController.getLastScore();
		if(time > 0) {
			graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 16));
			String lastScoreString = "+" + Integer.toString(lastScore);
			graphics.drawString(lastScoreString, 15 + fontMetrics.stringWidth(scoreString) + 15, 40);
		}
	}
	
	protected void paintFinal(Graphics2D graphics) {
		Image[] images = NodeController.getJudgeImages();
		int[] counts = NodeController.getJudgeCounts();
		int y = 2 + 110;
		int x1 = 52, x2 = 275;
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 26));
		graphics.setColor(GraphicsController.DARK_TOUCH_COLOR);
		for (int i = 0; i < images.length; i++) {
			graphics.drawImage(images[i], x1, y, this);
			graphics.drawString(Integer.toString(counts[i]), x2, y + 35);
			y += 55;
		}
		
		graphics.setFont(this.getFont("UD Digi Kyokasho NK-B", 22));
		
		y += 40;
		int maxCombo = NodeController.getMaxCombo();
		String comboString = "COMBO   " + Integer.toString(maxCombo);
		FontMetrics fontMetrics = graphics.getFontMetrics();
		graphics.drawString(comboString, (this.getWidth() - fontMetrics.stringWidth(comboString))/2, y);
		
		y += 35;
		int score = NodeController.getScore();
		String scoreString = "SCORE   " + Integer.toString(score);
		graphics.drawString(scoreString, (this.getWidth() - fontMetrics.stringWidth(scoreString))/2, y);
	}
	
	public void drawPolygon(Graphics2D graphics, int xPoints[], int yPoints[], int nPoints, Color color) {
		graphics.setColor(color);
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	public void fillPolygon(Graphics2D graphics, int xPoints[], int yPoints[], int nPoints, Color color) {
		graphics.setColor(color);
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}
	
	protected String getTimeString(long mills){
		int minute = (int)(mills / 60000);
		int second = (int)((mills / 1000) % 60);
		return (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
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
