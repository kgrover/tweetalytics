package com.example.tweetalytics;

import java.util.ArrayList;
import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.lymbix.LymbixClient;

@SuppressLint("NewApi")
public class SelectorActivity extends Activity {

	ListView main_list;
	ArrayList<String> trends;
	ArrayList<String> toAnalyze;
	ArrayList<Integer> positive = new ArrayList<Integer>();
	ArrayList<Integer> negative = new ArrayList<Integer>();
	String selected;
	ArrayList<String> hashtags = new ArrayList<String>();
	ProgressDialog dialog;
	ArrayList<String> popTweets = new ArrayList<String>();
	ArrayList<String> newTweets = new ArrayList<String>();
	final Handler switchToAnalytics = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			Intent intent = new Intent(SelectorActivity.this,
					AnalyticsSwipePage.class);
			intent.putIntegerArrayListExtra("positive", positive);
			intent.putIntegerArrayListExtra("negative", negative);
			intent.putStringArrayListExtra("newTweets", newTweets);
			intent.putStringArrayListExtra("hashtags", hashtags);
			if (popTweets == null) {
				System.out.println("NULL");
			}
			System.out.println("fwee:" + popTweets.toString());
			intent.putStringArrayListExtra("popTweets", popTweets);
			intent.putExtra("keyword", selected);

			SelectorActivity.this.startActivity(intent);

		}
	};
	final Thread t1 = new Thread() {
		@Override
		public void run() {
			LymbixClient lymbix = null;

			try {
				lymbix = new LymbixClient(
						"b8a75daac5712d8c6577ca15f81eadebc6000440");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				System.out
						.println("fweee"
								+ lymbix.tonalize("British Literature sucks ",
										null).DominantCategory);
			} catch (Exception e3) {
				System.out.println("fweee" + e3);
			}
			int counter = 0;
			for (String s : toAnalyze) {
				// System.out.println("ANALYZING: " + counter + s);
				counter++;
				try {
					String chin = lymbix.tonalize(s, null).DominantCategory;
					if (chin.equals("affection_friendliness")) {
						positive.set(0, positive.get(0) + 1);
					} else if (chin.equals("enjoyment_elation")) {
						positive.set(1, positive.get(1) + 1);
					} else if (chin.equals("amusement_excitement")) {
						positive.set(2, positive.get(2) + 1);
					} else if (chin.equals("contentment_gratitude")) {
						positive.set(3, positive.get(3) + 1);
					} else if (chin.equals("sadness_grief")) {
						negative.set(0, negative.get(0) + 1);
					} else if (chin.equals("anger_loathing")) {
						negative.set(1, negative.get(1) + 1);
					} else if (chin.equals("fear_uneasiness")) {
						negative.set(2, negative.get(2) + 1);
					} else if (chin.equals("humiliation_shame")) {
						negative.set(3, negative.get(3) + 1);
					}
				} catch (Exception e3) {
					System.out.println("fweee" + e3);
				}
			}

			System.out.println("DONE");
			Message msg = switchToAnalytics.obtainMessage();
			switchToAnalytics.sendMessage(msg);
		}
	};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);

		boolean what = this.getIntent().getBooleanExtra("refresh", false);
		if (what) {
			refresh();
		}
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		positive.add(0);
		positive.add(0);
		positive.add(0);
		positive.add(0);

		negative.add(0);
		negative.add(0);
		negative.add(0);
		negative.add(0);

		final EditText randomedit = (EditText) findViewById(R.id.randomedit);
		ImageButton search = (ImageButton) findViewById(R.id.imageButton1);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(SelectorActivity.this.getApplicationContext(),
						"Loading...Please Wait...", Toast.LENGTH_LONG).show();
				selected = randomedit.getText().toString();

				Thread thread = new Thread() {
					@Override
					public void run() {

						if (SelectorActivity.this == null) {
							System.out.println("AYY");
						}
						List<String> results = SelectorActivity.this
								.getSearchResults(selected);
						for (int i = 0; i < results.size(); i++) {
						}

						toAnalyze = (ArrayList<String>) results;
						t1.start();
					}
				};
				thread.start();
			}

		});

		main_list = (ListView) findViewById(R.id.main_list);
		main_list.setAdapter(new MainListAdapter(this, 3, null));

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAccessToken(
						"631594046-8sfuPCSrhuwz7k0u5LdvImzxk3413MiDZPLiHnvz")
				.setOAuthAccessTokenSecret(
						"P6JIEgpWB9U78DedksVpXysIj9STuRE8IedTi6nftw")
				.setOAuthConsumerKey("JUpldjf0WChzlpE1UZUA")
				.setOAuthConsumerSecret(
						"UqBhAIASk4WS2G0w70WRPmnLuW8pPVyYzDLAj0pDZQ");
		TwitterFactory tf = new TwitterFactory(cb.build());
		final Twitter twitter = tf.getInstance();
		final Query q = new Query("#yetizen");

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				System.out.println("Changing list!");
				trends = msg.getData().getStringArrayList("array");
				main_list.setAdapter(new MainListAdapter(SelectorActivity.this,
						3, msg.getData().getStringArrayList("array")));
			}
		};

		Thread thread = new Thread() {

			@Override
			public void run() {

				ResponseList<Trends> dailyTrends;

				Trends trends = null;
				try {
					trends = twitter.getPlaceTrends(23424977);
				} catch (TwitterException e1) {
					System.out.println("YOLO: " + e1);
				}
				ArrayList<String> currentTrends = new ArrayList<String>();
				for (Trend t : trends.getTrends()) {
					currentTrends.add(t.getName());
				}

				Message msg = handler.obtainMessage();
				Bundle b = new Bundle();
				b.putStringArrayList("array", currentTrends);
				msg.setData(b);
				handler.sendMessage(msg);

			}

		};
		thread.start();

		main_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Toast.makeText(SelectorActivity.this,
						"Loading...Please Wait..", Toast.LENGTH_LONG).show();

				Thread thread = new Thread() {
					@Override
					public void run() {

						List<String> results = SelectorActivity.this
								.getSearchResults(trends.get(arg2));
						for (int i = 0; i < results.size(); i++) {
						}

						toAnalyze = (ArrayList<String>) results;
						if (t1 != null) {
							t1.interrupt();

						}
						try {
							t1.start();
						} catch (Exception e) {
							new Thread() {
								@Override
								public void run() {
									LymbixClient lymbix = null;

									try {
										lymbix = new LymbixClient(
												"b8a75daac5712d8c6577ca15f81eadebc6000440");
									} catch (Exception e2) {
										e2.printStackTrace();
									}
									try {
										System.out.println("fweee"
												+ lymbix.tonalize(
														"British Literature sucks ",
														null).DominantCategory);
									} catch (Exception e3) {
										System.out.println("fweee" + e3);
									}
									int counter = 0;
									for (String s : toAnalyze) {
										// System.out.println("ANALYZING: " +
										// counter + s);
										counter++;
										try {
											String chin = lymbix.tonalize(s,
													null).DominantCategory;
											if (chin.equals("affection_friendliness")) {
												positive.set(0,
														positive.get(0) + 1);
											} else if (chin
													.equals("enjoyment_elation")) {
												positive.set(1,
														positive.get(1) + 1);
											} else if (chin
													.equals("amusement_excitement")) {
												positive.set(2,
														positive.get(2) + 1);
											} else if (chin
													.equals("contentment_gratitude")) {
												positive.set(3,
														positive.get(3) + 1);
											} else if (chin
													.equals("sadness_grief")) {
												negative.set(0,
														negative.get(0) + 1);
											} else if (chin
													.equals("anger_loathing")) {
												negative.set(1,
														negative.get(1) + 1);
											} else if (chin
													.equals("fear_uneasiness")) {
												negative.set(2,
														negative.get(2) + 1);
											} else if (chin
													.equals("humiliation_shame")) {
												negative.set(3,
														negative.get(3) + 1);
											}
										} catch (Exception e3) {
											System.out.println("fweee" + e3);
										}
									}

									System.out.println("DONE");
									Message msg = switchToAnalytics
											.obtainMessage();
									switchToAnalytics.sendMessage(msg);
								}
							}.start();
						}
					}
				};
				thread.start();

			}

		});

	}

	public void refresh() {
		dialog = ProgressDialog.show(SelectorActivity.this, "",
				"Loading. Please wait...", true);
		final String val = this.getIntent().getStringExtra("val");

		Thread thread = new Thread() {
			@Override
			public void run() {

				List<String> results = SelectorActivity.this
						.getSearchResults(val);
				for (int i = 0; i < results.size(); i++) {
				}

				toAnalyze = (ArrayList<String>) results;
				t1.start();
			}
		};
		thread.start();

	}

	public List<String> getSearchResults(String searchTerm) {

		selected = searchTerm;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAccessToken(
						"631594046-8sfuPCSrhuwz7k0u5LdvImzxk3413MiDZPLiHnvz")
				.setOAuthAccessTokenSecret(
						"P6JIEgpWB9U78DedksVpXysIj9STuRE8IedTi6nftw")
				.setOAuthConsumerKey("JUpldjf0WChzlpE1UZUA")
				.setOAuthConsumerSecret(
						"UqBhAIASk4WS2G0w70WRPmnLuW8pPVyYzDLAj0pDZQ");

		TwitterFactory tf = new TwitterFactory(cb.build());

		final Twitter twitter = tf.getInstance();
		final Query query = new Query(searchTerm);

		final Query query2 = new Query(searchTerm).resultType(Query.POPULAR);
		final Query query3 = new Query(searchTerm).resultType(Query.RECENT);

		QueryResult popular = null;
		QueryResult recent = null;

		try {
			query.setCount(10);
			popular = twitter.search(query2);
			recent = twitter.search(query3);
			List<Status> poplist = popular.getTweets();
			List<Status> recentlist = recent.getTweets();

			for (Status stat : poplist) {
				popTweets.add(stat.getText());
				System.out.println("ADDING : " + stat.getText());

			}

			for (Status stat : recentlist) {
				newTweets.add(stat.getText());
			}

		} catch (Exception e) {

		}
		// TODO: CHANGE!
		query.setCount(50);

		QueryResult result = null;
		ArrayList<String> tweettexts = new ArrayList<String>();
		ArrayList<String> list3 = null;
		try {
			result = twitter.search(query);
			if (result == null) {
				// System.out.println("AYY");
			}
			List<Status> list = result.getTweets();
			for (Status status : list) {
				tweettexts.add(status.getText());
			}

			for (int i = 0; i < 1; i++) {
				try {
					Query nextPage = result.nextQuery();
					QueryResult nextPageResult = twitter.search(nextPage);

					HashtagEntity[] list2;
					list3 = new ArrayList<String>();

					for (Status status : nextPageResult.getTweets()) {
						tweettexts.add(status.getText());
						list2 = status.getHashtagEntities();
						// System.out.println(Arrays.asList(list2));

						for (HashtagEntity element : list2) {
							list3.add(element.getText());
							// System.out.println("DANNY: " +
							// element.getText());
							// System.out.println("DANNY: " + list3);
						}

					}
					result = nextPageResult;
				} catch (Exception e) {
					// System.out.println("ERROR: " + e);

				}

				hashtags = list3;
			}
		} catch (TwitterException e) {
			System.out.println("EXCEPTION TWITTER: " + e.getRateLimitStatus());
		}

		return tweettexts;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selector, menu);
		return true;
	}

}
