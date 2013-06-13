package bbarm.android.luxuryvoca.sqlite;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class WordDBManager {

	public static void copyDB(Context context) {
		AssetManager manager = context.getAssets();
		String folderPath = context.getFilesDir().getPath()
				+ "data/bbarm.android.luxuryvoca/databases";
		String filePath = context.getFilesDir().getPath()
				+ "data/bbarm.android.luxuryvoca/databases/mvoca.db";
		File folder = new File(folderPath);
		File file = new File(filePath);
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			InputStream is = manager.open("mvoca.db");
			BufferedInputStream bis = new BufferedInputStream(is);

			if (folder.exists()) {
			} else {
				folder.mkdirs();
			}

			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, read);
			}

			bos.flush();

			bos.close();
			fos.close();
			bis.close();
			is.close();

		} catch (IOException e) {
			Log.e("ErrorMessage : ", e.getMessage());
		}
	}
}
