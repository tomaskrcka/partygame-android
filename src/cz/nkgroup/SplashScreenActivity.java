package cz.nkgroup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cz.nkgroup.db.WordDatabase;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.Settings;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;

public class SplashScreenActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen_layout);
		new Settings(this.getApplicationContext());
		new SoundDb(this.getApplicationContext());
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					WordDatabase wd = new WordDatabase(SplashScreenActivity.this);
					wd.fillDatabase();
					//sleep(2000);
				} catch (Exception e) {
					// do nothing
				} finally {
					finish();
					Intent intent = new Intent(SplashScreenActivity.this,
							cz.nkgroup.MainActivity.class);
					startActivity(intent);
				}
			}
		};
		
		
		
//		SharedPreferences pp = getSharedPreferences(SettingsActivity.PREFS_NAME, 0);
//		switch (pp.getInt("type", 2)) {
//		case 1:
//			GameDataDb.getInstance().getGameData().setJobTime(80);
//			break;
//
//		case 2:
//			GameDataDb.getInstance().getGameData().setJobTime(60);
//			break;
//			
//		case 3:
//			GameDataDb.getInstance().getGameData().setJobTime(45);
//			break;
//			
//		default:
//			GameDataDb.getInstance().getGameData().setJobTime(60);
//			break;
//		}
		splashThread.start();
	}
}
