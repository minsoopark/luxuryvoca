package bbarm.android.luxuryvoca;

import java.util.ArrayList;

import bbarm.android.luxuryvoca.adapter.DayGridAdapter;
import bbarm.android.luxuryvoca.model.Day;
import bbarm.android.luxuryvoca.sqlite.WordDB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class DaySelectActivity extends Activity {

	private static final int CHECK_WORD = 0;
	ViewPager dayViewPager;
	ViewPagerAdapter dayViewPagerAdapter;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && null != data) {
			switch (requestCode) {
			case CHECK_WORD:
				dayViewPagerAdapter.notifyDataSetChanged();
				break;
			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dayselect);
		
		setTitle(getResources().getText(R.string.study));

		dayViewPager = (ViewPager) findViewById(R.id.dayViewPager);

		dayViewPagerAdapter = new ViewPagerAdapter(this);
		dayViewPagerAdapter.setCount(3);
		dayViewPager.setAdapter(dayViewPagerAdapter);
		dayViewPager.setOffscreenPageLimit(3);
	}

	private class ViewPagerAdapter extends PagerAdapter {

		private LayoutInflater mInflater;
		private int mCount;

		public ViewPagerAdapter(Context context) {
			super();
			mInflater = LayoutInflater.from(context);
		}

		public void setCount(int count) {
			this.mCount = count;
		}

		@Override
		public int getCount() {
			return mCount;
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
			v = mInflater.inflate(R.layout.page_day, null);
			WordDB db = new WordDB(DaySelectActivity.this);

			GridView dayGridView = (GridView) v
					.findViewById(R.id.dayGridView);
			ArrayList<Day> dayList = new ArrayList<Day>();
			
			if (position == 0) {
				for (int i = 1; i <= 12; i++) {
					dayList.add(db.getDay(i));
				}
			} else if (position == 1) {
				for (int i = 13; i <= 24; i++) {
					dayList.add(db.getDay(i));
				}
			} else {
				for (int i = 25; i <= 35; i++) {
					dayList.add(db.getDay(i));
				}
			}

			DayGridAdapter dayGridAdapter = new DayGridAdapter(
					DaySelectActivity.this, dayList);
			dayGridView.setAdapter(dayGridAdapter);
			
			final int positionTmp = position;
			
			dayGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> av, View v,
						int index, long arg3) {
					Intent intent = new Intent(DaySelectActivity.this, WordViewActivity.class);
					intent.putExtra("day", positionTmp * 12 + index + 1);
					startActivityForResult(intent, CHECK_WORD);
				}
			});

			((ViewPager) pager).addView(v, 0);

			return v;
		}

		@Override
		public void destroyItem(View pager, int position, Object view) {
			((ViewPager) pager).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View pager, Object obj) {
			return pager == obj;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}
		
		@Override
		public int getItemPosition(Object object){
		     return POSITION_NONE;
		}

	}

}
