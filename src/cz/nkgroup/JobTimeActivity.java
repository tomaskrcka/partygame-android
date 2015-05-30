package cz.nkgroup;

import java.text.NumberFormat;

import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class JobTimeActivity extends BaseActivity {

	private GameData gameData;
	private SoundDb soundPool;

	private TextView clockTxt;
	private TextView jobTxt;

	private static final int DIALOG_SHOW_QUESTION = 5;

	private int time = 12;
	private Thread myRefreshThread = null;

	NumberFormat nf = NumberFormat.getInstance();

	Handler clockViewUpdateHandler = new Handler() {
		public void handleMessage(Message msg) {

			clockTxt.setText(nf.format(time / 60) + ":" + nf.format(time % 60));
			if (time == 0) {
				showDialog(DIALOG_SHOW_QUESTION);
			}
			time--;
			super.handleMessage(msg);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobtime_layout);
		nf.setMinimumIntegerDigits(2);
		soundPool = SoundDb.getInstance();
		gameData = GameDataDb.getInstance().getGameData();
		if (gameData.getResumeActivity() == JobTimeActivity.class) {
			time = gameData.getLastTime();
		} else {
			time = gameData.getJobTime();
		}
		prepareUI();
		myRefreshThread = new Thread(new CountDownRunner());
		myRefreshThread.start();
		jobTxt.setText(gameData.getJobstring());
	}

	private void prepareUI() {
		clockTxt = (TextView) findViewById(R.id.jobtime_txt_clock);
		jobTxt = (TextView) findViewById(R.id.jobtime_job);
	}

	@Override
	protected Class getClazz() {
		return JobTimeActivity.class;
	}

	class CountDownRunner implements Runnable {

		@Override
		public void run() {
			while (time >= 0) {
				Message m = new Message();
				JobTimeActivity.this.clockViewUpdateHandler.sendMessage(m);
				soundPool.playTick();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SHOW_QUESTION: {
			soundPool.playBell();
			return new AlertDialog.Builder(this)
					.setTitle(R.string.timeout)
					.setMessage(R.string.guessed)
					.setCancelable(true)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									nextActivity(true);
								}
							})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									nextActivity(false);
								}
							}).create();

		}
		case DIALOG_SHOW_BACK: {
			return new AlertDialog.Builder(this)
					.setTitle(R.string.pauseGame)
					.setMessage(R.string.backToMainMenu)
					.setCancelable(true)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									JobTimeActivity.this.finish();
									Intent intent = new Intent(
											JobTimeActivity.this,
											cz.nkgroup.MainActivity.class);
									GameDataDb.getInstance().getGameData()
											.setResumeActivity(getClazz());

									GameDataDb.getInstance().getGameData()
											.setLastTime(time);
									time = 0;
									startActivity(intent);
								}
							})
					.setNegativeButton(R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).create();
		}
		}
		return null;
	}

	public void onClickYesBtn(View target) {
		nextActivity(true);

	}

	public void onClickNoBtn(View target) {
		nextActivity(false);

	}

	private void nextActivity(boolean ok) {
		time = -1;
		try {
			this.myRefreshThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int score = gameData.getPoints();
		if (ok) {
			soundPool.playWinner();
			gameData.getActualPlayer().setScore(
					gameData.getActualPlayer().getScore() + score);
		} else {
			if (gameData.isRandom()) {
				if (score == 4) {
					gameData.getActualPlayer()
							.setScore(
									gameData.getActualPlayer().getScore()
											- (score - 2));
				} else {
					gameData.getActualPlayer()
							.setScore(
									gameData.getActualPlayer().getScore()
											- (score - 1));
				}
			}
			soundPool.playLooser();
		}

		finish();
		Intent intent;
		if (gameData.getActualPlayer().getScore() == 15) {
			intent = new Intent(this, cz.nkgroup.WinnerActivity.class);
		} else {
			intent = new Intent(this, cz.nkgroup.ScoreActivity.class);
		}
		startActivity(intent);
	}

}
