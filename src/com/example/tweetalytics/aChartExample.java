package com.example.tweetalytics;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class aChartExample {

	public Intent execute(Context context, ArrayList<Integer> positive,
			ArrayList<Integer> negative) {

		int[] colors = new int[] { Color.rgb(0, 0, 255), Color.rgb(0, 0, 215),
				Color.rgb(0, 0, 175), Color.rgb(0, 0, 135),
				Color.rgb(255, 0, 0), Color.rgb(215, 0, 0),
				Color.rgb(175, 0, 0), Color.rgb(135, 0, 0) };
		DefaultRenderer renderer = buildCategoryRenderer(colors);

		CategorySeries categorySeries = new CategorySeries("Breakdown");
		categorySeries.add("Friendliness", positive.get(0));
		categorySeries.add("Enjoyment", positive.get(1));
		categorySeries.add("Amusement ", positive.get(2));
		categorySeries.add("Satisfaction ", positive.get(3));
		categorySeries.add("Sadness ", negative.get(0));
		categorySeries.add("Anger ", negative.get(1));
		categorySeries.add("Fear ", negative.get(2));
		categorySeries.add("Humiliation ", negative.get(3));

		return ChartFactory.getPieChartIntent(context, categorySeries,
				renderer, null);
	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
			renderer.setAntialiasing(true);
			renderer.setChartTitle("Emotion Breakdown");
			renderer.setLabelsTextSize(30);
			renderer.setLegendTextSize(20);
			renderer.setLabelsColor(Color.DKGRAY);
			int[] margins = { 20, 20, 20, 20 };
			renderer.setMargins(margins);
		}
		return renderer;
	}
}