package cz.nkgroup.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import cz.nkgroup.R;
import cz.nkgroup.objdb.Settings;

public class WordDatabase {

	private static final String DATABASE_NAME = "word.db";
	private static final String WORD_TABLE = "words";
	private static final int DATABASE_VERSION = 4;

	private static final String KEY_ID = "id";
	private static final String KEY_WORD = "word";
	private static final String KEY_LANGUAGE = "language";
	private static final String KEY_FLAG = "flag";
	private static final String KEY_PRIORITY = "priority";
	private static final String KEY_THEME = "theme";
	private final DatabaseHelper dbhelper;
	private static int i = 0;

	public WordDatabase(Context context) {
		dbhelper = new DatabaseHelper(context);
		dbhelper.getWritableDatabase();
	}

	public void fillDatabase() throws IOException {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ WORD_TABLE
				+ " (id Integer, word TEXT, language TEXT, priority Integer, flag Integer, theme Integer)");
		ContentValues values = new ContentValues();
		values.put(KEY_FLAG, 0);
		int count = db.update(WORD_TABLE, values, null, null);
		if (count < 95) {
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			dbhelper.onCreate(db);
		}
		db.close();
	}

	// public void refreshDB() throws IOException {
	// ContentValues values = new ContentValues();
	// values.put(KEY_FLAG, 0);
	// SQLiteDatabase db = dbhelper.getWritableDatabase();
	// db.update(WORD_TABLE, values, null, null);
	// db.close();
	// }

	public String getRandomWord(String language) {
		if (!language.equalsIgnoreCase("cz")) {
			language = "us";
		}
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String query = KEY_LANGUAGE + " like '%" + language + "%' and "
				+ KEY_FLAG + "=0 and (";
		for (Integer cat : Settings.getInstance().getCategories()) {
			query += "" + KEY_THEME + "=" + cat.toString() + " or ";
		}
		query = query.substring(0, query.length() - 4);
		query += ")";
		Cursor c = db.query(WORD_TABLE, null, query, null, null, null, null);
		Random r = new Random();
		if (c.getCount() > 0) {
			int i = r.nextInt(c.getCount());
			c.move(i + 1);
			ContentValues values = new ContentValues();
			values.put(KEY_FLAG, 1);
			db.update(
					WORD_TABLE,
					values,
					KEY_ID
							+ "="
							+ String.valueOf(c.getInt(c.getColumnIndex(KEY_ID))),
					null);
			String res = c.getString(c.getColumnIndex(KEY_WORD));
			c.close();
			db.close();
			return res;
		}

		return "Slova vycerpana";

	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;
		private static final String WORD_TABLE_CREATE = "CREATE TABLE "
				+ WORD_TABLE
				+ " (id Integer, word TEXT, language TEXT, priority Integer, flag Integer, theme Integer)";

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mHelperContext = context;

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			mDatabase.execSQL(WORD_TABLE_CREATE);
			try {
				loadWords(db);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			onCreate(db);

		}

		// public void onOpen(SQLiteDatabase db) {
		// db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
		// onCreate(db);
		// }

		public void loadWords(SQLiteDatabase db) throws IOException {
			// this.getWritableDatabase()
			this.mDatabase = db;
			if (mDatabase == null) {
				return;
			}
			final Resources resources = mHelperContext.getResources();
			InputStream inputStream = resources.openRawResource(R.raw.dics);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));

			try {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] strings = TextUtils.split(line, ";");
					if (strings.length < 3)
						continue;
					long id = addWord(Integer.valueOf(strings[0].trim()),
							strings[1].trim(),
							Integer.valueOf(strings[2].trim()),
							strings[3].trim());

				}
			} finally {
				reader.close();
			}
		}

		/**
		 * Add a word to the dictionary.
		 * 
		 * @return rowId or -1 if failed
		 */
		private long addWord(Integer priority, String word, Integer categoryId,
				String language) {
			ContentValues initialValues = new ContentValues();
			i++;
			initialValues.put(KEY_ID, i);
			initialValues.put(KEY_WORD, word);
			initialValues.put(KEY_LANGUAGE, language);
			initialValues.put(KEY_PRIORITY, priority);
			initialValues.put(KEY_THEME, categoryId);
			initialValues.put(KEY_FLAG, 0);

			return mDatabase.insert(WORD_TABLE, null, initialValues);
		}
	}
}
