package com.example.tweetalytics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.viewpager.extensions.FixedTabsView;

public class AnalyticsSwipePage extends FragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analytics_main);
		this.setTitle("Tweet Analysis");
		ViewPager mainView = (ViewPager) findViewById(R.id.pager);
		mainView.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), this.getApplicationContext()));
		FixedTabsView mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_icon_tabs);
		com.example.custom.adapter.FixedIconTabsAdapter mFixedTabsAdapter = new com.example.custom.adapter.FixedIconTabsAdapter(
				this);

		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mainView);

		// make sure it starts at the middle page
		mainView.setCurrentItem(1);
	}

	private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		public MyFragmentPagerAdapter(FragmentManager fm, Context c) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			if (index == 0) {
				return PopTweetsFragment.newInstance(1);
			} else if (index == 1) {
				return AnalyticsFragment.newInstance(1);
			} else if (index == 2) {
				return TimeViewFragment.newInstance(2);
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}
	}
}
