package com.example.custom.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.astuetz.viewpager.extensions.TabsAdapter;
import com.astuetz.viewpager.extensions.ViewPagerTabButton;
import com.example.tweetalytics.R;

public class FixedIconTabsAdapter implements TabsAdapter {

	private final Activity mContext;

	private final int[] mIcons = { R.drawable.send_holo, R.drawable.send_holo,
			R.drawable.send_holo };

	public FixedIconTabsAdapter(Activity ctx) {
		mContext = ctx;
	}

	@Override
	public View getView(int position) {
		ViewPagerTabButton tab;

		LayoutInflater inflater = mContext.getLayoutInflater();
		tab = (ViewPagerTabButton) inflater.inflate(R.layout.tab_fixed_icon,
				null);

		if (position < mIcons.length) {
			tab.setCompoundDrawablesWithIntrinsicBounds(null, mContext
					.getResources().getDrawable(mIcons[position]), null, null);
		}

		return tab;
	}

}
