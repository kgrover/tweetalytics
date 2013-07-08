package com.example.tweetalytics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TimeViewFragment extends Fragment {

	static TimeViewFragment somePage;
	String selected;
	ArrayList<String> popTweets;
	ArrayList<String> newTweets;
	ArrayList<String> hashtags;

	static TimeViewFragment newInstance(int number) {
		TimeViewFragment f = new TimeViewFragment();
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.time_view, container, false);
		ListView a = (ListView) view.findViewById(R.id.lv2);
		List<String> hashtag_new = null;
		try {
			hashtag_new = new ArrayList<String>(new HashSet<String>(this
					.getActivity().getIntent()
					.getStringArrayListExtra("hashtags")));
		} catch (Exception e) {

		}
		a.setAdapter(new MainListAdapter(this.getActivity()
				.getApplicationContext(), 10, (ArrayList<String>) hashtag_new));

		final ArrayList<String> hashtags_nw = (ArrayList<String>) hashtag_new;
		a.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(TimeViewFragment.this.getActivity(),
						SelectorActivity.class);
				intent.putExtra("refresh", true);
				intent.putExtra("val", hashtags_nw.get(arg2));
				startActivity(intent);

			}

		});

		return view;

	}

}
