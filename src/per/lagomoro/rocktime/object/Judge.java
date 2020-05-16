package per.lagomoro.rocktime.object;

public class Judge {

	public String name;
	public String picName;
	public long permitTime;
	public double scorePercent;
	public int scoreAdd;
	public boolean combo;
	
	public Judge(String name, String picName, long permitTime, double scorePercent, int scoreAdd, boolean combo) {
		this.name = name;
		this.picName = picName;
		this.permitTime = permitTime;
		this.scorePercent = scorePercent;
		this.scoreAdd = scoreAdd;
		this.combo = combo;
	}
	
	public int count = 0;
	
	public void reset() {
		count = 0;
	}

}
