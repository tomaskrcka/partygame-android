package cz.nkgroup.objdb;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

	private static Settings instance;
	private SharedPreferences preference;
	public static final String PREFS_NAME = "partygame_settings";

	public static final int SETTINGS_GAMEEASY = 1;
	public static final int SETTINGS_GAMEMEDIUM = 2;
	public static final int SETTINGS_GAMEHARD = 3;

	public static final int SETTINGS_SPORT = 1;
	public static final int SETTINGS_JOBS = 2;
	public static final int SETTINGS_SEX = 3;

	private static final String SETTINGS_GAMEDIFF = "gamediff";
	private static final String SETTINGS_CATEGORYSPORT = "category_sport";
	private static final String SETTINGS_CATEGORYJOBS = "category_jobs";
	private static final String SETTINGS_CATEGORYSEX = "category_sex";
	
	private static final String SETTINGS_SOUND = "sound";

	public static Settings getInstance() {
		if (instance == null) {
			throw new Error("Neni initialize");
		}
		return instance;
	}

	public Settings(Context context) {
		preference = context.getSharedPreferences(PREFS_NAME, 0);
		instance = this;
	}

	public void setGameType(int type) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putInt(SETTINGS_GAMEDIFF, type);
		editor.commit();
	}

	public int getGameType() {
		return preference.getInt(SETTINGS_GAMEDIFF, SETTINGS_GAMEMEDIUM);
	}

	public ArrayList<Integer> getCategories() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (preference.getBoolean(SETTINGS_CATEGORYSPORT, true))
			res.add(SETTINGS_SPORT);

		if (preference.getBoolean(SETTINGS_CATEGORYJOBS, true))
			res.add(SETTINGS_JOBS);

		if (preference.getBoolean(SETTINGS_CATEGORYSEX, false))
			res.add(SETTINGS_SEX);

		if (res.size() == 0) {
			res.add(SETTINGS_SPORT);
			res.add(SETTINGS_JOBS);
		}

		return res;
	}

	public void setCategory(int categoryId, boolean state) {
		SharedPreferences.Editor editor = preference.edit();
		switch (categoryId) {
		case SETTINGS_SPORT:
			editor.putBoolean(SETTINGS_CATEGORYSPORT, state);
			break;

		case SETTINGS_JOBS:
			editor.putBoolean(SETTINGS_CATEGORYJOBS, state);
			break;

		case SETTINGS_SEX:
			editor.putBoolean(SETTINGS_CATEGORYSEX, state);
			break;
		}
		editor.commit();
	}
	
	public boolean isSound() {
		return preference.getBoolean(SETTINGS_SOUND, true);
	}
	
	public void setSound(boolean state) {
		SharedPreferences.Editor editor = preference.edit();
		editor.putBoolean(SETTINGS_SOUND, state);
		editor.commit();
	}
	

}
