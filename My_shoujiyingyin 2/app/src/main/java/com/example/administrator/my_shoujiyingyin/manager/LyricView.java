package com.example.administrator.my_shoujiyingyin.manager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.LyricItem;

import java.util.ArrayList;

public class LyricView extends View {
	private int defaultColor = Color.WHITE;
	private int highlightColor = Color.GREEN;
	private float                defaultSize;
	private float                highlightSize;
	private ArrayList<LyricItem> lyrics;
	private int                  highlightIndex;
	private Paint                paint;
	private float                hightlightY;
	private float                rowHeight;
	private int                  currentPosition;

	public LyricView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		defaultSize = getResources().getDimension(R.dimen.default_lyric_size);
		highlightSize = getResources().getDimension(R.dimen.highlight_lyric_size);
		paint = new Paint();
		paint.setColor(defaultColor);
		paint.setTextSize(defaultSize);
		paint.setAntiAlias(true);
		rowHeight = getTextHeight("") + 50;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		if (lyrics == null || lyrics.isEmpty()) {
			drawCenterText(canvas, "");
			return;
		}
		
		LyricItem highlightLyric = lyrics.get(highlightIndex);
		if (highlightIndex != lyrics.size() - 1) {
			transaltionCanvas(canvas, highlightLyric);
		}
		drawLyrics(canvas, highlightLyric);
	}


	private void drawLyrics(Canvas canvas, LyricItem highlightLyric) {
		String text = highlightLyric.text;
		drawCenterText(canvas, text);
		for (int i = 0; i < highlightIndex; i++) {
			float y = hightlightY - (highlightIndex - i) * rowHeight;
			drawHorizotalText(canvas, lyrics.get(i).text, y, false);
		}
		for (int i = highlightIndex + 1; i < lyrics.size(); i++) {
			float y = hightlightY + (i - highlightIndex) * rowHeight;
			drawHorizotalText(canvas, lyrics.get(i).text, y, false);
		}
	}


	private void transaltionCanvas(Canvas canvas, LyricItem highlightLyric) {
		long showedTime = currentPosition - highlightLyric.startShowTime;
		long totalShowTime = lyrics.get(highlightIndex + 1).startShowTime - highlightLyric.startShowTime;
		float scale = ((float) showedTime) / totalShowTime;
		float translationY = scale * rowHeight;
		canvas.translate(0, -translationY);
	}

	private void drawCenterText(Canvas canvas, String text) {
		int textHeight = getTextHeight(text);
		hightlightY = getHeight() / 2 + textHeight / 2;
		drawHorizotalText(canvas, text, hightlightY, true);
	}

	private void drawHorizotalText(Canvas canvas, String text, float y, boolean isHighlight) {
		paint.setColor(isHighlight ? highlightColor : defaultColor);
		paint.setTextSize(isHighlight ? highlightSize : defaultSize);
		
		int textWidth = getTextWidth(text);

		float x = getWidth() / 2 - textWidth / 2;
		
		canvas.drawText(text, x, y, paint);
	}

	private int getTextHeight(String text) {
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		int textHeight = bounds.height();
		return textHeight;
	}

	private int getTextWidth(String text) {
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		int textWidth = bounds.width();
		return textWidth;
	}


	public void setCurrentPosition(int currentPosition) {
		if (lyrics == null || lyrics.isEmpty()) {
			return;
		}
		
		this.currentPosition = currentPosition;
		for (int i = 0; i < lyrics.size(); i++) {
			LyricItem lyric = lyrics.get(i);
			if (currentPosition >= lyric.startShowTime) {
				if (i == lyrics.size() - 1) {
					highlightIndex = i;
					break;
				} else if (currentPosition < lyrics.get(i + 1).startShowTime) {
					highlightIndex = i;
					break;
				}
			}
		}
		invalidate();
	}


	public void setMusicPath(String musicPath) {
		highlightIndex = 0;
		lyrics = LyricLoader.loadLyric(musicPath);
		invalidate();
	}

}















