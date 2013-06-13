package bbarm.android.luxuryvoca.sqlite;

import java.util.ArrayList;

import bbarm.android.luxuryvoca.model.Day;
import bbarm.android.luxuryvoca.model.Word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WordDB {
	public static final int NORMAL = 0;
	public static final int CHECKED_ONLY = 1;
	
	SQLiteDatabase db;
	ContentValues row;
	Context ctx;
	WordDBHelper mHelper;

	public WordDB(Context context) {
		this.ctx = context;
		mHelper = new WordDBHelper(ctx);
	}
/*
	public ArrayList<Word> getFromOptions(int id, String word, String mean,
			int day, boolean checked) {

	}
	
	public Word getFromId(int id) {
		
    }

	public Word getFromMean(String mean) {

	}

	public Word getFromWord(String word) {

	}
*/
	
	public Day getDay(int day) {
		
		int allWordsCount = getFromDay(day, NORMAL).size();
		int checkedWordsCount;
		try {
			checkedWordsCount = getFromDay(day, CHECKED_ONLY).size();
		} catch (NullPointerException e) {
			checkedWordsCount = 0;
		}
		
		Day oneDay = new Day(day, checkedWordsCount, allWordsCount);
		
		return oneDay;
	}
	
	public ArrayList<Word> getFromDay(int day, int option) {
		ArrayList<Word> wordList = new ArrayList<Word>();
		Cursor cursor;
		String query;
		String selectString = "select * from wordbook where wordbook='day";

		db = mHelper.getReadableDatabase();
		
		query = selectString + String.format("%02d", day) + "'";
		
		if(option == CHECKED_ONLY) {
			query += " and check_yn='Y'";
		}
		
		cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				int id = Integer.parseInt(cursor.getString(0));
				String wordday = cursor.getString(1);
				String word = cursor.getString(2);
				String mean = cursor.getString(3);
				String pronun = cursor.getString(5);
				boolean checked = cursor.getString(4).equals("Y") ? true
						: false;

				Word oneWord = new Word(id, wordday, word, mean, pronun,
						checked);
				wordList.add(oneWord);
			} while (cursor.moveToNext());
		} else {
			wordList = null;
		}

		cursor.close();
		mHelper.close();

		return wordList;
	}
	
	public void checkWord(int id, boolean checked) {
		String query = "update wordbook set check_yn='" + (checked?"Y":"N") + "' where _id=" + id;
		db = mHelper.getWritableDatabase();
		db.execSQL(query);
		mHelper.close();
	}

}
