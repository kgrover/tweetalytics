package com.example.tweetalytics;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AnalyticsPage extends Activity {

	ArrayList<Integer> positive;
	ArrayList<Integer> negative;
	EditText searchedit;

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.analyticspage);
		int[] colors = new int[] { Color.rgb(0, 0, 255), Color.rgb(0, 0, 215),
				Color.rgb(0, 0, 175), Color.rgb(0, 0, 135),
				Color.rgb(255, 0, 0), Color.rgb(215, 0, 0),
				Color.rgb(175, 0, 0), Color.rgb(135, 0, 0) };
		searchedit = (EditText) findViewById(R.id.searchedit);
		searchedit.setText(this.getIntent().getStringExtra("keyword"));

		positive = this.getIntent().getIntegerArrayListExtra("positive");
		negative = this.getIntent().getIntegerArrayListExtra("negative");
		LinearLayout linlayout = (LinearLayout) this
				.findViewById(R.id.linlayout);

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

		GraphicalView mChartView = ChartFactory.getPieChartView(
				this.getApplicationContext(), categorySeries, renderer);
		linlayout.addView(mChartView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

	}

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
			renderer.setAntialiasing(true);
			renderer.setChartTitle("Emotion Breakdown");
			renderer.setLabelsTextSize(20);
			renderer.setLegendTextSize(15);
			renderer.setZoomEnabled(false);
			renderer.setPanEnabled(true);
			renderer.setLabelsColor(Color.DKGRAY);
			int[] margins = { 20, 20, 20, 20 };
			renderer.setMargins(margins);
		}
		return renderer;
	}
}
