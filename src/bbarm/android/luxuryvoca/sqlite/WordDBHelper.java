package bbarm.android.luxuryvoca.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
	
	Context mContext;

	public WordDBHelper(Context context) {
		super(context, context.getFilesDir().getPath()
				+ "data/bbarm.android.luxuryvoca/databases/mvoca.db", null, 1);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
