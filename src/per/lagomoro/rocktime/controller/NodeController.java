package per.lagomoro.rocktime.controller;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import per.lagomoro.rocktime.object.DrawNode;
import per.lagomoro.rocktime.object.Hard;
import per.lagomoro.rocktime.object.Judge;
import per.lagomoro.rocktime.object.Rail;

public class NodeController {
	
	private static int keyCount = 0;
	
	private static int maxScore = 0;
	private static int plusScore = 0;
	private static double score = 0;
	private static int lastScore = 0;
	private static int scoreTime = 0;
	
	private static int maxCombo = 0;
	private static int combo = 0;
	
	private static long fever = 0;
	private static int feverY = 0;
	
	private static int lastJudge = 0;
	private static int judgeY = 0;
	
	private static String filename = "";
	private static int hard = 0;
	
	//每秒钟下落200像素
	private static int speed = 200;
	private static int fix = 0;
	
	private static ArrayList<Judge> judgeList = new ArrayList<Judge>();
	private static ArrayList<Rail> railList = new ArrayList<Rail>();
	private static ArrayList<Hard> hardList = new ArrayList<Hard>();
	
	public static void init() {
		judgeList.add(new Judge("perfect_plus", "perfect_plus.png", 13*2, 1.0, 1, true));
		judgeList.add(new Judge("perfect"     , "perfect.png"     , 25*2, 1.0, 0, true));
		judgeList.add(new Judge("great"       , "great.png"       , 50*2, 0.8, 0, true));
		judgeList.add(new Judge("bad"         , "bad.png"         , 60*2, 0.4, 0, false));
		judgeList.add(new Judge("miss"        , "miss.png"        , 60*2, 0.0, 0, false));
	
		railList.add(new Rail(68));
		railList.add(new Rail(70));
		railList.add(new Rail(74));
		railList.add(new Rail(75));
		
		hardList.add(new Hard("EASY"  , Color.GREEN));
		hardList.add(new Hard("NORMAL", Color.YELLOW));
		hardList.add(new Hard("HARD"  , Color.ORANGE));
		hardList.add(new Hard("EXPERT", Color.RED));
		hardList.add(new Hard("DEVIL" , Color.MAGENTA));
		
		loadOption();
		saveOption();
	}
	
	public static void loadOption(){
		loadOption("./option.ini");
	}
	
	public static void saveOption(){
		saveOption("./option.ini");
	}

	private static void loadOption(String filename){
		File file = new File(filename);
		try {
			if(file.exists()) {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader input = new BufferedReader(reader);
				String[] info = null;
				String line = null;
				while ((line = input.readLine()) != null) {
					info = line.split("\t");
					switch(info[0]) {
					case "speed" : speed = Integer.parseInt(info[1]); break;
					case "delay" : fix = Integer.parseInt(info[1]); break;
					}
				}
				reader.close();
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void saveOption(String filename){
		try {
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.write("speed\t" + speed + "\n");
			fileWriter.write("delay\t" + fix + "\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static long getJudgeTime() {
		return MusicController.getCurrentTime() + fix;
	}
	
	public static boolean process(long timestamp) {
		long time = Math.abs(timestamp - getJudgeTime());
		for (int i = 0; i < judgeList.size() - 1; i++) {
			Judge judge = judgeList.get(i);
			if(time <= judge.permitTime) {
				processJudge(i);
				return true;
			}
		}
		return false;
	}
	
	public static void processJudge(int index) {
		Judge judge = judgeList.get(index);
		judge.count ++;
		double tempScore = score;
		score += ((double)(maxScore) / keyCount * judge.scorePercent);
		plusScore += judge.scoreAdd;
		lastScore = (int)(score + 0.5) - (int)(tempScore + 0.5) + judge.scoreAdd;
		scoreTime = 60;
		combo = (judge.combo ? combo + 1 : 0);
		maxCombo = combo > maxCombo ? combo : maxCombo;
		lastJudge = index;
		judgeY = 10;
	}
	
	public static boolean isInBound(long timestamp) {
		return (Math.abs(timestamp - getJudgeTime()) <= judgeList.get(judgeList.size() - 1).permitTime);
	}
	
	public static boolean isMiss(long timestamp) {
		return (getJudgeTime() > timestamp && getJudgeTime() - timestamp > judgeList.get(judgeList.size() - 1).permitTime);
	}
	
	public static void processMiss() {
		processJudge(judgeList.size() - 1);
	}
	
	public static void reset() {
		for(Rail rail : railList)
			rail.reset();
		for(Judge judge : judgeList)
			judge.reset();

		keyCount = 0;
		
		maxScore = 0;
		plusScore = 0;
		score = 0;
		lastScore = 0;
		scoreTime = 0;
		
		maxCombo = 0;
		combo = 0;
		
		fever = 0;
		feverY = 0;
		
		filename = "";
		hard = 0;
		
		lastJudge = 0;
		judgeY = 0;
	}
	
	public static void loadNotes(String filename){
		reset();
		File file = new File(filename);
		try {
			if(file.exists()) {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader input = new BufferedReader(reader);
				Pattern pattern = Pattern.compile("(\\d{1,2}):(\\d{1,2}).(\\d{1,3})");
				String[] info = null;
				String line = null;
				while ((line = input.readLine()) != null) {
					info = line.split("\t");
					switch(info[0]) {
					case "note" : keyCount += addNote(info, pattern); break;
					case "hold" : keyCount += addHold(info, pattern); break;
					case "speed": addSpeed(info, pattern); break;
					case "score": setScore(info); break;
					case "hard" : setHard(info); break;
					case "music": setFilename(info); break;
					case "fever": setFever(info, pattern); break;
					default:
					}
				}
				for(Rail rail : railList)
					rail.sort();
				reader.close();
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int addNote(String[] info, Pattern pattern) {
		Matcher matcher = pattern.matcher(info[2]);
		if(matcher.find()) {
			int rail = Integer.parseInt(info[1]) - 1;
			long timestamp = getTimeStamp(matcher.group(1), matcher.group(2), matcher.group(3));
			railList.get(rail).addNote(timestamp);
			return 1;
		}
		return 0;
	}
	
	private static int addHold(String[] info, Pattern pattern) {
		ArrayList<Long> timestampList = new ArrayList<Long>();
		Matcher matcher;
		int rail = Integer.parseInt(info[1]) - 1;
		for (int i = 2; i < info.length; i++) {
			matcher = pattern.matcher(info[i]);
			if(matcher.find()) {
				timestampList.add(getTimeStamp(matcher.group(1), matcher.group(2), matcher.group(3)));
			}
		}
		railList.get(rail).addHold(timestampList.toArray(new Long[1]));
		return timestampList.size();
	}
	private static void addSpeed(String[] info, Pattern pattern) {

	}
	private static void setScore(String[] info) {
		maxScore = Integer.parseInt(info[1]);
	}
	private static void setHard(String[] info) {
		hard = Integer.parseInt(info[1]);
	}
	private static void setFilename(String[] info) {
		filename = info[1];
	}
	private static void setFever(String[] info, Pattern pattern) {
		Matcher matcher = pattern.matcher(info[2]);
		if(matcher.find()) {
			long timestamp = getTimeStamp(matcher.group(1), matcher.group(2), matcher.group(3));
			fever = timestamp;
		}
	}
	
	private static long getTimeStamp(String minute, String second, String mills) {
		if(mills.length() < 3) mills += "0";
		if(mills.length() < 2) mills += "00";
		return Integer.parseInt(minute) * 60000 + Integer.parseInt(second) * 1000 + Integer.parseInt(mills);
	}
	
	public static void update() {
		for(Rail rail : railList)
			rail.update();
		if(getJudgeTime() > fever && Math.abs(getJudgeTime() - fever) < 100) {
			feverY = 30;
		};
	}
	
	public static void inputPress(int keyCode) {
		for(Rail rail : railList)
			rail.inputPress(keyCode);
	}
	
	public static void inputRelease(int keyCode) {
		for(Rail rail : railList)
			rail.inputRelease(keyCode);
	}
	
	public static int getDistance(long startTime, long endTime) {
		return (int)(((double)(endTime - startTime)) * speed / 1000);
	}
	
	public static Boolean[] getRailPressed() {
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for(Rail rail : railList)
			list.add(rail.pressed);
		return list.toArray(new Boolean[1]);
	}
	
	public static ArrayList<ArrayList<DrawNode>> getRailNodes() {
		ArrayList<ArrayList<DrawNode>> list = new ArrayList<ArrayList<DrawNode>>();
		for(Rail rail : railList)
			list.add(rail.getDrawNodes());
		return list;
	}
	
	public static int getRailCount() {
		return railList.size();
	}
	
	public static int getSpeed() {
		return speed;
	}
	
	public static boolean getFever() {
		return fever != 0 && getJudgeTime() >= fever;
	}
	
	public static int getFeverY() {
		return (feverY > 0 ? feverY-- : 0);
	}
	
	public static Image getLastJudgeImage() {
		if(combo > 0 || !judgeList.get(lastJudge).combo) {
			Image image = new ImageIcon("./assets/images/" + judgeList.get(lastJudge).picName).getImage();
			return image;
		}
		return null;
	}
	
	public static Image[] getJudgeImages() {
		ArrayList<Image> imageList = new ArrayList<Image>();
		for(Judge judge : judgeList) {
			imageList.add(new ImageIcon("./assets/images/" + judge.picName).getImage());
		}
		return imageList.toArray(new Image[1]);
	}
	
	public static int[] getJudgeCounts() {
		int[] countList = new int[judgeList.size()];
		for (int i = 0; i < judgeList.size(); i++) {
			countList[i] = judgeList.get(i).count;
		}
		return countList;
	}
	
	public static int getJudgeY() {
		return (judgeY > 0 ? judgeY-- : 0);
	}
	
	public static int getCombo() {
		return combo;
	}
	
	public static int getMaxCombo() {
		return maxCombo;
	}
	
	public static int getScore() {
		return (int)(score + 0.5) + plusScore;
	}
	
	public static int getScoreTime() {
		return (scoreTime > 0 ? scoreTime-- : 0);
	}
	
	public static int getLastScore() {
		return lastScore;
	}
	
	public static double getDrawSpeed() {
		return (double)speed / 20;
	}
	
	public static void setSpeed(double number) {
		int tempSpeed = speed + (int)(number * 20);
		speed = Math.min(Math.max(tempSpeed, 20), 500);
		saveOption();
	}
	
	public static double getDrawDelay() {
		return (double)fix / 10;
	}
	
	public static void setDelay(double number) {
		int tempFix = fix + (int)(number * 100);
		fix = Math.min(Math.max(tempFix, -500), 500);
		saveOption();
	}
	
	public static Hard getHard() {
		return hardList.get(hard);
	}
	
	public static String getFilename() {
		return filename;
	}
}
