package per.lagomoro.rocktime.object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import per.lagomoro.rocktime.controller.NodeController;
import per.lagomoro.rocktime.ui.MainWindow;
import per.lagomoro.rocktime.ui.ViewMusic;

public class Rail {

	public int keyCode;
	public boolean pressed;
	
	public Rail(int keyCode) {
		this.keyCode = keyCode;
	}
	
	public LinkedList<Node> nodeList = new LinkedList<Node>();
	
	public void reset() {
		this.nodeList.clear();
	}
	
	public void addNote(long timestamp) {
		this.nodeList.add(new Note(timestamp));
	};
	
	public void addHold(Long[] timestamp) {
		this.nodeList.add(new Hold(timestamp));
	};
	
	public void sort() {
		this.nodeList.sort(new Comparator<Node>() {
			@Override
			public int compare(Node node1, Node node2) {
				return (int)(node1.getStartTime() - node2.getStartTime());
			}
		});
	};	
	
	public void update() {
		if(this.nodeList.size() > 0) {
			Node node = nodeList.get(0);
			node.update();
			if(node.delete) nodeList.remove(0);
		}
	}
	
	public synchronized void inputPress(int keyCode) {
		if(keyCode != this.keyCode) return;
		if(pressed) return;
		pressed = true;
		if(this.nodeList.size() > 0) {
			Node node = nodeList.get(0);
			node.inputPress();
			if(node.delete) nodeList.remove(0);
		}
	}
	
	public synchronized void inputRelease(int keyCode) {
		if(keyCode != this.keyCode) return;
		pressed = false;
		if(this.nodeList.size() > 0) {
			Node node = nodeList.get(0);
			node.inputRelease();
			if(node.delete) nodeList.remove(0);
		}
	}
	
	public ArrayList<DrawNode> getDrawNodes() {
		ArrayList<DrawNode> list = new ArrayList<DrawNode>();
		int distance = MainWindow.HEIGHT - ViewMusic.LINE_PLACE - 4;
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			int startDistance = NodeController.getDistance(NodeController.getJudgeTime(), node.getStartTime());
			if(startDistance > distance) continue;
			list.add(node.getDrawNode());
		}
		return list;
	}

}
