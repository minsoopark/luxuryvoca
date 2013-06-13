package bbarm.android.luxuryvoca;

import java.util.ArrayList;
import java.util.Collections;

import bbarm.android.luxuryvoca.model.Word;
import bbarm.android.luxuryvoca.sqlite.WordDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizActivity extends Activity implements View.OnClickListener{

	TextView wordTextView;
	Button firstButton;
	Button secondButton;
	Button thirdButton;
	Button forthButton;
	ImageView oImageView;
	ProgressBar progressBar;

	ArrayList<Word> wordList = new ArrayList<Word>();

	WordDB db;

	Word currentWord;
	int currentIndex = 0;
	
	int correctAnsPosition;

	int[] answerIndexes = new int[4];
	
	Animation fadeInAnim, nextWordAnim, correctedAnim, scaleOutAnim;
	
	Vibrator viberator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		setTitle(getResources().getText(R.string.quiz));
		
		wordTextView = (TextView) findViewById(R.id.wordTextView);
		firstButton = (Button) findViewById(R.id.firstButton);
		secondButton = (Button) findViewById(R.id.secondButton);
		thirdButton = (Button) findViewById(R.id.thirdButton);
		forthButton = (Button) findViewById(R.id.forthButton);
		oImageView = (ImageView) findViewById(R.id.oImageView);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		wordTextView.setVisibility(View.GONE);
		
		fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		nextWordAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		correctedAnim = AnimationUtils.loadAnimation(this, R.anim.scale_in);
		scaleOutAnim = AnimationUtils.loadAnimation(this, R.anim.scale_out);

		Intent intent = getIntent();
		Bundle extra = intent.getExtras();
		CharSequence day =  extra.getCharSequence("day");
		String dayString = (String) day.subSequence(4, day.length());
		int dayInt = Integer.parseInt(dayString);

		db = new WordDB(this);

		wordList = db.getFromDay(dayInt, WordDB.NORMAL);
		Collections.shuffle(wordList);

		firstButton.setOnClickListener(this);
		secondButton.setOnClickListener(this);
		thirdButton.setOnClickListener(this);
		forthButton.setOnClickListener(this);
		
		progressBar.setMax(wordList.size());
		
		viberator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		nextWordAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				wordTextView.startAnimation(fadeInAnim);
				wordTextView.setText(currentWord.getWord());
				firstButton.setText(wordList.get(answerIndexes[0]).getMean());
				secondButton.setText(wordList.get(answerIndexes[1]).getMean());
				thirdButton.setText(wordList.get(answerIndexes[2]).getMean());
				forthButton.setText(wordList.get(answerIndexes[3]).getMean());
			}
		});
		
		correctedAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				oImageView.setVisibility(View.VISIBLE);
				setButtonsEnabled(false);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				oImageView.startAnimation(scaleOutAnim);
			}
		});
		
		scaleOutAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				oImageView.setVisibility(View.GONE);
				setButtonsEnabled(true);
			}
		});
		
		initWord();
	}
	
	private void initWord() {
		progressBar.setProgress(currentIndex + 1);
		currentWord = wordList.get(currentIndex);
		int[] randomPositions = new int[3];
		for(int i = 0 ; i < 3 ; i ++) {
			int a;
			do {
				a = (int)(Math.random() * wordList.size());
			} while (a == currentIndex);
			randomPositions[i] = a;
		}
		
		correctAnsPosition = (int)(Math.random() * 4);

		int tmp = 0;
		for(int j = 0 ; j < 4 ; j ++) {
			if(j == correctAnsPosition) {
				answerIndexes[j] = currentIndex;
			} else {
				answerIndexes[j] = randomPositions[tmp];
				tmp++;
			}
		}
		
		wordTextView.startAnimation(nextWordAnim);
		wordTextView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		int selected = Integer.parseInt((String)v.getTag());
		if(selected == correctAnsPosition) {
			oImageView.startAnimation(correctedAnim);
			if(currentIndex == wordList.size() - 1) {
				showDialog();
			} else {
				currentIndex++;
				initWord();
			}
		} else {
			viberator.vibrate(100);
		}
	}
	
	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage("끝~ 수고했어!");
		dialog.setPositiveButton("퀴즈 종료", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.show();
	}
	
	private void setButtonsEnabled(boolean enabled) {
		firstButton.setClickable(enabled);
		secondButton.setClickable(enabled);
		thirdButton.setClickable(enabled);
		forthButton.setClickable(enabled);
	}
}
