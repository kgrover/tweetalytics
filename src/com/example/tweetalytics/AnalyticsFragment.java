package com.example.tweetalytics;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AnalyticsFragment extends Fragment {

	static AnalyticsSwipePage somePage;

	static AnalyticsFragment newInstance(int number) {
		AnalyticsFragment f = new AnalyticsFragment();
		Log.d("ArrayListFragment", "newInstance");
		Bundle args = new Bundle();
		args.putInt("number", number);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this.getActivity(),
					SelectorActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.analyticspage, container, false);
		int[] colors = new int[] { Color.rgb(0, 0, 255), Color.rgb(0, 0, 215),
				Color.rgb(0, 0, 175), Color.rgb(0, 0, 135),
				Color.rgb(255, 0, 0), Color.rgb(215, 0, 0),
				Color.rgb(175, 0, 0), Color.rgb(135, 0, 0) };
		EditText searchedit = (EditText) view.findViewById(R.id.searchedit);
		searchedit.setText(AnalyticsFragment.this.getActivity().getIntent()
				.getStringExtra("keyword"));

		ArrayList<Integer> positive = this.getActivity().getIntent()
				.getIntegerArrayListExtra("positive");
		ArrayList<Integer> negative = this.getActivity().getIntent()
				.getIntegerArrayListExtra("negative");
		LinearLayout linlayout = (LinearLayout) view
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
				AnalyticsFragment.this.getActivity().getApplicationContext(),
				categorySeries, renderer);
		linlayout.addView(mChartView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return view;
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
			renderer.setLegendTextSize(50);
			renderer.setZoomEnabled(false);
			renderer.setPanEnabled(false);
			renderer.setLabelsColor(Color.DKGRAY);
			int[] margins = { 20, 20, 20, 20 };
			renderer.setMargins(margins);
		}
		return renderer;
	}

}
