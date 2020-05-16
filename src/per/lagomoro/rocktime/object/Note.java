package per.lagomoro.rocktime.object;

import java.awt.Color;

import per.lagomoro.rocktime.controller.NodeController;

public class Note extends Node{

	private long timestamp;
	
	public Color color1 = new Color(185, 228, 255);
	public Color color2 = new Color(77 , 203, 255);
	public Color color3 = new Color(54 , 156, 255);
	
	public Note(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public void update() {
		if(NodeController.isMiss(timestamp)) {
			NodeController.processMiss();
			this.delete = true;
		}
	}
	
	@Override
	public void inputPress() {
		if(NodeController.process(timestamp)) {
			this.delete = true;
		}
	}
	
	@Override
	public void inputRelease() {}
	
	@Override
	public long getStartTime() {
		return this.timestamp;
	}
	
	@Override
	public long getFinishTime() {
		return this.timestamp;
	}

	@Override
	public DrawNode getDrawNode() {
		int startY = NodeController.getDistance(NodeController.getJudgeTime(), this.getStartTime());
		int endY = NodeController.getDistance(NodeController.getJudgeTime(), this.getFinishTime());
		return new DrawNode(startY, endY, this.color1, this.color2, this.color3, true, false);
	}
	
}
