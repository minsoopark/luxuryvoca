package bbarm.android.luxuryvoca.adapter;

import java.util.ArrayList;

import bbarm.android.luxuryvoca.R;
import bbarm.android.luxuryvoca.model.Day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DayGridAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Day> mList;

	public DayGridAdapter(Context context, ArrayList<Day> list) {
		super();
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int index) {
		return mList.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		DayViewHolder viewHolder;
		LayoutInflater li = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = convertView;
		Day day = mList.get(index);
		
		if (v == null) {
			viewHolder = new DayViewHolder();

			v = li.inflate(R.layout.each_day, null);

			viewHolder.dayTextView = (TextView) v
					.findViewById(R.id.dayTextView);
			viewHolder.studyProgressBar = (ProgressBar) v
					.findViewById(R.id.studyProgressBar);

			v.setTag(viewHolder);
		} else {
			viewHolder = (DayViewHolder) v.getTag();
		}

		viewHolder.dayTextView.setText(String.format("%02d", day.getDay()));
		viewHolder.studyProgressBar.setProgress(day.getCheckedWordsCount()*100/day.getAllWordsCount());

		return v;
	}

	class DayViewHolder {
		private TextView dayTextView;
		private ProgressBar studyProgressBar;
	}
}
