package cz.nkgroup;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cz.nkgroup.db.WordDatabase;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;
import cz.nkgroup.vo.GameData.GAMETYPE;

public class JobActivity extends BaseActivity {

	private ImageView imgTop;
	private ImageView imgBottom;
	private TextView jobTypeTxt;
	private TextView jobTxt;

	private GameData gameData;

	NumberFormat nf = NumberFormat.getInstance();
	
	private SoundDb soundPool;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nf.setMinimumIntegerDigits(2);
		setContentView(R.layout.job_layout);
		prepareUI();
		gameData = GameDataDb.getInstance().getGameData();
		changeGUI();
		
		soundPool = SoundDb.getInstance();
	}

	private void prepareUI() {
		imgTop = (ImageView) findViewById(R.id.job_img_top);
		imgBottom = (ImageView) findViewById(R.id.job_img_bottom);
		jobTxt = (TextView) findViewById(R.id.job_txt_job);
		jobTypeTxt = (TextView) findViewById(R.id.job_txt_jobtype);
	}

	private void changeGUI() {
		if (gameData.getGametype() == GAMETYPE.ART) {
			imgTop.setImageResource(R.drawable.job_draw);
			imgBottom.setImageResource(R.drawable.job_draw2);
			jobTypeTxt.setText(R.string.draw);
		} else {
			imgTop.setImageResource(R.drawable.job_art);
			imgBottom.setImageResource(R.drawable.job_art);
			jobTypeTxt.setText(R.string.pantomime);
		}

		WordDatabase db = new WordDatabase(this);
		String locale = Locale.getDefault().getCountry();
		String word = db.getRandomWord(locale);
		gameData.setJobstring(word);
		jobTxt.setText(word);

	}

	@Override
	protected Class getClazz() {
		return JobActivity.class;
	}
	
	public void onClickStartBtn(View target) {
		soundPool.playClick();
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.JobTimeActivity.class);
		
		startActivity(intent);
	}

}
