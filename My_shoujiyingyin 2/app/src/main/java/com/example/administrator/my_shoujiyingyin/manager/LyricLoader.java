package com.example.administrator.my_shoujiyingyin.manager;
import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
import com.example.administrator.my_shoujiyingyin.bean.LyricItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class LyricLoader {

	
	public static void main(String[] args) {
		AudioItem audioItem=new AudioItem();
		String musicPath = audioItem.getPath();
		ArrayList<LyricItem> lyrics = loadLyric(musicPath);
		for (LyricItem lyricItem : lyrics) {
			System.out.println(lyricItem);
		}
	}
	public static ArrayList<LyricItem> loadLyric(String musicPath) {
		ArrayList<LyricItem> lyrics = null;

		String prefix = musicPath.substring(0, musicPath.lastIndexOf("."));

		File lrcFile = new File(prefix + ".lrc");
		File txtFile = new File(prefix + ".txt");
		if (lrcFile.exists()) {
			lyrics = readLyricFile(lrcFile);
		} else if (txtFile.exists()) {
			lyrics = readLyricFile(txtFile);
		}
		if (lyrics == null || lyrics.isEmpty()) {
			return null;
		}
		Collections.sort(lyrics);

		return lyrics;
	}


	private static ArrayList<LyricItem> readLyricFile(File lrcFile) {
		ArrayList<LyricItem> lyrics = new ArrayList<LyricItem>();
		try {
			InputStream in = new FileInputStream(lrcFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] strings = line.split("]");
				String lyricText = strings[strings.length - 1];
				for (int i = 0; i < strings.length - 1; i++) {
					long startShowTime = parseTime(strings[i]);
					lyrics.add(new LyricItem(startShowTime, lyricText));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lyrics;
	}


	private static long parseTime(String time) {
		String minute = time.substring(1, 3);
		String second = time.substring(4, 6);
		String millis = time.substring(7, 9);
		
		long minuteMillis = Integer.parseInt(minute) * 60 * 1000;
		long secondMillis = Integer.parseInt(second) * 1000;
		long millisecond = Integer.parseInt(millis) * 10;
		
		return minuteMillis + secondMillis + millisecond;
	}

}
