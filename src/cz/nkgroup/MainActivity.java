package cz.nkgroup;

import java.io.IOException;

import cz.nkgroup.db.WordDatabase;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	private Button btnresume;
	private Button btngame;
	private Button btnsettings;
	private Button btnhelp;

	private SoundDb soundPool;

	private LinearLayout llayoutbtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		prepareUI();
		if (GameDataDb.getInstance().getGameData().getResumeActivity() == null) {
			llayoutbtn.removeView(btnresume);
		}

		soundPool = new SoundDb(this.getApplicationContext());
	}

	public void onClickGameBtn(View target) {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.PlayerActivity.class);
		startActivity(intent);
		GameDataDb.getInstance().getGameData().removeAllPlayers();
		WordDatabase wd = new WordDatabase(this);
		try {
			wd.fillDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		soundPool.playClick();
		//
	}

	public void onClickResumeBtn(View target) {
		this.finish();
		Intent intent = new Intent(this, GameDataDb.getInstance().getGameData()
				.getResumeActivity());
		startActivity(intent);
		soundPool.playClick();
		//
	}

	public void onClickHelpBtn(View target) {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.HelpActivity.class);
		startActivity(intent);
		soundPool.playClick();
	}

	public void onClickSettingsBtn(View target) {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.SettingsActivity.class);
		startActivity(intent);
		soundPool.playClick();
	}
	
	private void prepareUI() {
		btnresume = (Button) this.findViewById(R.id.main_btn_resume);
		btngame = (Button) this.findViewById(R.id.main_btn_game);
		btnsettings = (Button) this.findViewById(R.id.main_btn_settings);
		btnhelp = (Button) this.findViewById(R.id.main_btn_help);
		llayoutbtn = (LinearLayout) this.findViewById(R.id.main_llayout_btn);
	}
}