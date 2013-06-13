package bbarm.android.luxuryvoca;

import java.util.ArrayList;
import java.util.Locale;

import bbarm.android.luxuryvoca.model.Word;
import bbarm.android.luxuryvoca.sqlite.WordDB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class WordViewActivity extends Activity {
	
	SeekBar pageSeekBar;
	TextView numberTextView;
	ViewPager wordViewPager;
	ViewPagerAdapter wordViewPagerAdapter;
	
	ArrayList<Word> wordList = new ArrayList<Word>();
	int day;
	
	Intent intent;
	
	WordDB db;
	
	TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_wordview);

	    pageSeekBar = (SeekBar) findViewById(R.id.pageSeekBar);
		numberTextView = (TextView) findViewById(R.id.numberTextView);
	    wordViewPager = (ViewPager) findViewById(R.id.wordViewPager);
	    
	    wordViewPagerAdapter = new ViewPagerAdapter(this);
		
	    intent = getIntent();
	    day = intent.getIntExtra("day", 0);
	    
	    setTitle("Day " + day);
	    
	    db = new WordDB(this);
	    wordList = db.getFromDay(day, WordDB.NORMAL);

		numberTextView.setText(String.format("%2d/%2d", 1, wordList.size()));
	    
	    wordViewPager.setAdapter(wordViewPagerAdapter);
	    wordViewPager.setOffscreenPageLimit(wordList.size());
	    wordViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int page) {
				numberTextView.setText(String.format("%2d/%2d", page + 1, wordList.size()));
				pageSeekBar.setProgress(page + 1);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    pageSeekBar.setMax(wordList.size());
	    pageSeekBar.setProgress(1);
	    pageSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				wordViewPager.setCurrentItem(seekBar.getProgress() - 1);
				numberTextView.setBackgroundColor(Color.argb(0, 0, 0, 0));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				numberTextView.setBackgroundColor(Color.argb(50, 227, 54, 103));
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				numberTextView.setText(String.format("%2d/%2d", seekBar.getProgress(), wordList.size()));
			}
		});

		tts = new TextToSpeech(WordViewActivity.this, new OnInitListener() {
			
			@Override
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS) {
					int result = tts.setLanguage(Locale.US);
					if(result == TextToSpeech.LANG_MISSING_DATA ||
							result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Toast.makeText(WordViewActivity.this, "음성 사용 불가", Toast.LENGTH_SHORT).show();
					}
					tts.setSpeechRate(0.8f);
				} else {
					Toast.makeText(WordViewActivity.this, "음성 사용 불가", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private class ViewPagerAdapter extends PagerAdapter {

		private LayoutInflater mInflater;
		
		public ViewPagerAdapter(Context context) {
			super();
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return wordList.size();
		}

		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
			v = mInflater.inflate(R.layout.page_word, null);
			
			Word word = wordList.get(position);
			
			TextView wordTextView = (TextView) v.findViewById(R.id.wordTextView);
			TextView meanTextView = (TextView) v.findViewById(R.id.meanTextView);
			CheckBox studyCheckBox = (CheckBox) v.findViewById(R.id.studyCheckBox);
			Button playButton = (Button) v.findViewById(R.id.playButton);
			
			wordTextView.setText(word.getWord());
			meanTextView.setText(word.getMean());
			studyCheckBox.setChecked(word.isChecked());
			
			final Word wordTmp = word;
			
			studyCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton v, boolean checked) {
					db.checkWord(wordTmp.getId(), checked);
					setResult(RESULT_OK, intent);
				}
			});
			
			playButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					tts.speak(wordTmp.getWord(), TextToSpeech.QUEUE_FLUSH, null);
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
	}

}
