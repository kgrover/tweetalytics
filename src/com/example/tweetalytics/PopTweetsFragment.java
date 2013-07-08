package com.example.tweetalytics;

import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PopTweetsFragment extends Fragment {

	static AnalyticsSwipePage somePage;

	static PopTweetsFragment newInstance(int number) {
		PopTweetsFragment f = new PopTweetsFragment();
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
		View view = inflater.inflate(R.layout.poptweets, container, false);
		TextView recent = (TextView) view.findViewById(R.id.recent);
		recent.setMovementMethod(new ScrollingMovementMethod());

		TextView poptweets = (TextView) view.findViewById(R.id.tweets);
		List<String> tweets = this.getActivity().getIntent()
				.getStringArrayListExtra("popTweets");
		List<String> newtweets = this.getActivity().getIntent()
				.getStringArrayListExtra("newTweets");
		String done = "";
		String new1 = "";
		for (String tw : tweets) {
			done += tw + "\n";

		}

		for (String tw : newtweets) {
			new1 += tw + "\n";

		}
		poptweets.setText(done);
		recent.setText(new1);

		return view;
	}
}
