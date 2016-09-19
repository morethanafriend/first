package com.example.administrator.my_shoujiyingyin.bean;


public class LyricItem implements Comparable<LyricItem>{


	public long startShowTime;

	public String text;
	
	public LyricItem(long startShowTime, String text) {
		super();
		this.startShowTime = startShowTime;
		this.text = text;
	}

	@Override
	public String toString() {
		return "LyricItem [startShowTime=" + startShowTime + ", text=" + text + "]";
	}

	@Override
	public int compareTo(LyricItem o) {
		return Long.valueOf(startShowTime).compareTo(o.startShowTime);
	}
	
}
