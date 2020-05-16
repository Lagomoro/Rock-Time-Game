package per.lagomoro.rocktime.object;

import java.awt.Color;

import per.lagomoro.rocktime.controller.NodeController;

public class Hold extends Node{

	private Long[] timestamp;
	private int currentIndex;
	
	private boolean pressed;
	
	public Color color1 = new Color(255, 248, 209);
	public Color color2 = new Color(255, 248, 231);
	public Color color3 = new Color(255, 213,  25);
	
	public Hold(Long[] timestamp) {
		this.timestamp = timestamp;
		currentIndex = 0;
	}
	
	public long getCurrentTimeStamp() {
		return this.timestamp[this.currentIndex];
	}
	public boolean isFirstIndex() {
		return this.currentIndex == 0;
	}
	public boolean isFinished() {
		return this.currentIndex == this.timestamp.length;
	}
	
	@Override
	public void update() {
		if(this.pressed && !this.isFirstIndex()) {
			if(NodeController.isInBound(this.getCurrentTimeStamp())) {
				NodeController.processJudge(0);
				this.currentIndex ++;
			}
		}else{
			if(NodeController.isMiss(this.getCurrentTimeStamp())) {
				NodeController.processMiss();
				this.currentIndex ++;
			}
		}
		if(this.isFinished()) this.delete = true;
	}
	
	@Override
	public void inputPress() {
		if(this.isFirstIndex()) {
			if(NodeController.process(this.getCurrentTimeStamp())) {
				this.currentIndex ++;
			}
		}
		this.pressed = true;
	}
	
	@Override
	public void inputRelease() {
		this.pressed = false;
	}
	
	@Override
	public long getStartTime() {
		return this.timestamp[0];
	}
	
	@Override
	public long getFinishTime() {
		return this.timestamp[this.timestamp.length - 1];
	}
	
	@Override
	public DrawNode getDrawNode() {
		int startY = this.isFirstIndex() ? NodeController.getDistance(NodeController.getJudgeTime(), this.getStartTime()) : 0;
		int endY = NodeController.getDistance(NodeController.getJudgeTime(), this.getFinishTime());
		boolean drawHead = this.isFirstIndex();
		return new DrawNode(startY, endY, this.color1, this.color2, this.color3, drawHead, true);
	}
	
}
