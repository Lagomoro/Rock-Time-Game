package pers.lagomoro.rocktime.controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class MusicController {

	private static final int RATE = 512;
	private static final int RAISE_HINT = 2;
	private static final int RAISE_COLOR = 8;
	
	private static double[] fftSource = new double[RATE];
	private static double[] drawInstance = new double[128];
	private static double[] dropInstance = new double[128];
	private static int[] dropTicks = new int[128];
	private static int pointer = 0;
	
	private static int playStatus = 0;
	private static String nowMusic = "";
	
	private static Color playColor = new Color(230, 230, 230);
	
	private static long fixTime = 0;
	
	private static long maxTime = 0;
	private static long currentTime = 0;
	private static long waitTime = 5000;
	
	private static final int MAX_VALUE = 500;
	private static final int MIN_VALUE_COLOR = 180;

	public static void reset() {
		maxTime = 0;
		currentTime = 0;
		waitTime = 5000;
		playColor = new Color(230, 230, 230);
		fixTime = 0;
	}
	
	private static void putByte(int channel, float rate, byte[] buffer) {
		if(channel == 2) {
			if(rate == 16) {
				put((buffer[1] << 8) | buffer[0]);
				put((buffer[3] << 8) | buffer[2]);
			} else {
				put(buffer[1]);
				put(buffer[3]);
			}
		} else {
			if(rate == 16) {
				put((buffer[1] << 8) | buffer[0]);
				put((buffer[3] << 8) | buffer[2]);
			} else {
				put(buffer[1]);
				put(buffer[3]);
			}
		}
	}
	
	private static void put(double value) {
		fftSource[pointer] = value;
		pointer ++;
		if(pointer == fftSource.length) {
			pointer = 0;
			calculate();
			fftSource = new double[RATE];
		}
	}
	
	private static void calculate(){
		if(fftSource.length > 400) {
			FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
			Complex[] result = fft.transform(fftSource, TransformType.FORWARD);
			double temp = 0;
			int count = 0;
			double tempColor = 0;
			int countColor = 0;
			for(int i = 0, j = 0, k = 0; i < fftSource.length/4; i++, j++, k++) {
				if(j == fftSource.length / 4 / 128) {
					drawInstance[count] = temp / 44100 * 300 * RAISE_HINT;
					if(drawInstance[count] > MAX_VALUE) drawInstance[count] = MAX_VALUE;
					
					dropInstance[count] = Math.max(dropInstance[count] - (double)dropTicks[count]/4, 0);
					if(dropInstance[count] < drawInstance[count]) {
						dropInstance[count] = (int) drawInstance[count];
						dropTicks[count] = 1;
					}else {
						dropTicks[count] ++;
					}
					j = 0;
					temp = 0;
					count ++;
				}
				if(k == fftSource.length / 4 / 4) {
					double value = tempColor / 44100 * 255 * RAISE_COLOR;
					changeColor(countColor, (int) (countColor == 0 ? Math.max(value/ 5, MIN_VALUE_COLOR) : countColor == 1 ? Math.max(value, MIN_VALUE_COLOR) : Math.max(value*2, MIN_VALUE_COLOR)));
					k = 0;
					tempColor = 0;
					countColor ++;
				}
				double abs = result[i].abs();
				tempColor += abs;
				temp += abs;
			}
		}
	}
	
	private static void changeColor(int place, int value) {
		switch (place) {
		case 0:playColor = new Color(
				Math.min(playColor.getRed() + (value - playColor.getRed())/8, 255),
				Math.max(playColor.getGreen() - 5, MIN_VALUE_COLOR),
				Math.max(playColor.getBlue() - 5, MIN_VALUE_COLOR));break;
		case 1:playColor = new Color(
				Math.max(playColor.getRed() - 5, MIN_VALUE_COLOR),
				Math.min(playColor.getGreen() + (value - playColor.getGreen())/8, 255),
				Math.max(playColor.getBlue() - 5, MIN_VALUE_COLOR));break;
		case 2:playColor = new Color(
				Math.max(playColor.getRed() - 5, MIN_VALUE_COLOR),
				Math.max(playColor.getGreen() - 5, MIN_VALUE_COLOR),
				Math.min(playColor.getBlue() + (value - playColor.getBlue())/8, 255));break;
		}
	}
	
	public static void play(String filepath) {
		if(playStatus > 0) return;
		reset();
		new Thread(() -> {
			AudioInputStream inputStream = null, inputStream2 = null;
			AudioFormat audioFormat = null;
			SourceDataLine player = null;
			try {
				File file = new File(filepath);
				if(!file.exists()) {
					nowMusic = "乐谱损坏或音频文件丢失";
					return;
				}
				nowMusic = file.getName().substring(0, file.getName().length() - 4);
				
				inputStream2 = AudioSystem.getAudioInputStream(file);
				audioFormat = inputStream2.getFormat();
				if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 
							16, audioFormat.getChannels(), audioFormat.getChannels() * 2,audioFormat.getSampleRate(), false);
					inputStream = AudioSystem.getAudioInputStream(audioFormat, inputStream2);
				}else {
					inputStream = inputStream2;
				}
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				maxTime = clip.getMicrosecondLength()/1000;
				clip.close();
				inputStream.close();
				inputStream2.close();
				
				inputStream2 = AudioSystem.getAudioInputStream(file);
				audioFormat = inputStream2.getFormat();
				if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 
							16, audioFormat.getChannels(), audioFormat.getChannels() * 2,audioFormat.getSampleRate(), false);
					inputStream = AudioSystem.getAudioInputStream(audioFormat, inputStream2);
				}else {
					inputStream = inputStream2;
				}
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
				player = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				player.open(audioFormat);
				while(waitTime > 0) {waitTime --;Thread.sleep(1);}
				playStatus = 1;
				player.start();
				byte[] buffer = new byte[4];
				int length;
				while((length = inputStream.read(buffer)) != -1 && playStatus > 0) {
					putByte(inputStream.getFormat().getChannels(), inputStream.getFormat().getSampleRate(), buffer);
					fixTime = new Date().getTime();
					currentTime = player.getMicrosecondPosition()/1000;
					player.write(buffer, 0, length);
					while(playStatus > 1) {player.stop();}
					player.start();
				}
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				try {
					if(inputStream != null) inputStream.close();
					if(inputStream2 != null) inputStream2.close();
					if(player != null) player.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			playStatus = 0;
		}).start();
	}
	
	public static void stop() {
		playStatus = 0;
	}
	
	public static void pause() {
		playStatus = 2;
	}
	
	public static void replay() {
		playStatus = 1;
	}
	
	public static double[] getDrawInstance() {
		return drawInstance;
	}
	
	public static double[] getDropInstance() {
		return dropInstance;
	}
	
	public static int getPlayStatus() {
		return playStatus;
	}
	
	public static Color getPlayColor() {
		return playColor;
	}
	
	public static String getNowMusic() {
		return nowMusic;
	}
	
	public static double getProcess() {
		return ((double)currentTime)/maxTime;
	}
	
	public static long getCurrentTime() {
		long time = new Date().getTime() - fixTime - waitTime;
		return fixTime == 0 ? 0 - waitTime: playStatus == 2 ? currentTime : currentTime + time;
	}
	
	public static long getDrawTime() {
		long time = new Date().getTime() - fixTime - waitTime;
		return playStatus == 1 ? currentTime + time : currentTime;
	}
	
	public static long getMaxTime() {
		return maxTime;
	}
	
	public static long getWaitTime() {
		return waitTime;
	}
	
}
