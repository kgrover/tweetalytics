package com.example.tweetalytics;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainListAdapter extends BaseAdapter {
	private final LayoutInflater inflater;
	int count;
	Context c;
	ArrayList<String> trends;

	public MainListAdapter(Context context, int count, ArrayList<String> data) {
		c = context;
		inflater = LayoutInflater.from(context);
		this.count = count;
		trends = data;

	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		convertView = inflater.inflate(R.layout.main_list_item, null);
		TextView trend_text = (TextView) convertView
				.findViewById(R.id.trend_text);

		if (!(trends == null) && trends.size() <= position) {
			trend_text.setText("--");

		} else {
			try {
				if (!(trends == null || trends.get(position) == null || trends
						.get(position).equals("null"))) {
					trend_text.setText(trends.get(position));
				} else {
					trend_text.setText("Loading...");

				}
			} catch (Exception e) {

			}
		}

		return convertView;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
