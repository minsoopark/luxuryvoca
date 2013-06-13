package bbarm.android.luxuryvoca;

import bbarm.android.luxuryvoca.sqlite.WordDBManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;

public class MainActivity extends Activity implements View.OnClickListener {

	CharSequence[] array = { "Day 1", "Day 2", "Day 3", "Day 4", "Day 5",
			"Day 6", "Day 7", "Day 8", "Day 9", "Day 10", "Day 11", "Day 12",
			"Day 13", "Day 14", "Day 15", "Day 16", "Day 17", "Day 18",
			"Day 19", "Day 20", "Day 21", "Day 22", "Day 23", "Day 24",
			"Day 25", "Day 26", "Day 27", "Day 28", "Day 29", "Day 30",
			"Day 31", "Day 32", "Day 33", "Day 34", "Day 35" };
	Button studyButton;
	Button quizButton;
	
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		studyButton = (Button) findViewById(R.id.studyButton);
		quizButton = (Button) findViewById(R.id.quizButton);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);

		if (pref.getBoolean("first", true)) {
			WordDBManager.copyDB(this);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("first", false);
			editor.commit();
		}

		studyButton.setOnClickListener(this);
		quizButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==studyButton.getId()) {
			intent = new Intent(MainActivity.this, DaySelectActivity.class);
			startActivity(intent);
		} else {
			intent = new Intent(MainActivity.this, QuizActivity.class);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setItems(array, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					intent.putExtra("day", array[which]);
					startActivity(intent);
				}
			});
			dialog.setTitle("√©≈Õ º±≈√");
			dialog.show();
		}
	}
}
