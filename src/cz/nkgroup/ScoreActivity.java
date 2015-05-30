package cz.nkgroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.view.ScoreTubeView;
import cz.nkgroup.vo.GameData;
import cz.nkgroup.vo.PlayerVO;

public class ScoreActivity extends BaseActivity {

	private LinearLayout mScoreTubeLayout1;
	private LinearLayout mScoreTubeLayout2;
	private LinearLayout mScoreTubeLayout3;
	private LinearLayout mScoreScoreLayout;
	private ScoreTubeView mScoreTubeView_red;
	private ScoreTubeView mScoreTubeView_green;
	private ScoreTubeView mScoreTubeView_yellow;
	private TextView mScoreTxt1;
	private TextView mScoreTxt2;
	private TextView mScoreTxt3;
	
	private TextView mScoreScore1;
	private TextView mScoreScore2;
	private TextView mScoreScore3;

	private Button scoreStartBtn;

	private GameData gameData;

	private PlayerVO nextPlayer;
	
	private SoundDb soundPool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_layout);
		prepareUI();

		gameData = GameDataDb.getInstance().getGameData();

		gameData.setResumeActivity(ScoreActivity.class);

		changeGUI();
		soundPool = SoundDb.getInstance();	
	}

	private void prepareUI() {
		mScoreTubeLayout1 = (LinearLayout) findViewById(R.id.score_tubelayout1);
		mScoreTubeLayout2 = (LinearLayout) findViewById(R.id.score_tubelayout2);
		mScoreTubeLayout3 = (LinearLayout) findViewById(R.id.score_tubelayout3);
		mScoreScoreLayout = (LinearLayout) findViewById(R.id.score_scorelayout);
		mScoreTubeView_green = (ScoreTubeView) findViewById(R.id.scoretube_green);
		mScoreTubeView_red = (ScoreTubeView) findViewById(R.id.scoretube_red);
		mScoreTubeView_yellow = (ScoreTubeView) findViewById(R.id.scoretube_yellow1);

		mScoreTxt1 = (TextView) findViewById(R.id.score_txt_name1);
		mScoreTxt2 = (TextView) findViewById(R.id.score_txt_name2);
		mScoreTxt3 = (TextView) findViewById(R.id.score_txt_name3);
		
		mScoreScore1 = (TextView) findViewById(R.id.score_score1);
		mScoreScore2 = (TextView) findViewById(R.id.score_score2);
		mScoreScore3 = (TextView) findViewById(R.id.score_score3);

		scoreStartBtn = (Button) findViewById(R.id.score_btn_start);
	}

	private void changeGUI() {
		if (gameData != null) {
			if (gameData.getNumOfPlayers() == 2) {
				mScoreScoreLayout.removeView(mScoreTubeLayout3);				
			}
			mScoreTxt1.setText(gameData.getPlayerNumber(1).getPlayerName());
			mScoreTxt2.setText(gameData.getPlayerNumber(2).getPlayerName());
			mScoreScore1.setText(String.valueOf(gameData.getPlayerNumber(1).getScore()));
			mScoreScore2.setText(String.valueOf(gameData.getPlayerNumber(2).getScore()));
			if (gameData.getNumOfPlayers() > 2) {
				mScoreTxt3.setText(gameData.getPlayerNumber(3).getPlayerName());
				mScoreScore3.setText(String.valueOf(gameData.getPlayerNumber(3).getScore()));
			}
			nextPlayer = gameData.nextPlayer();
			scoreStartBtn.setText(nextPlayer.getPlayerName());
			mScoreTubeView_red.setScore(gameData.getPlayers().get(0)
					.getScore());
			mScoreTubeView_green
					.setScore(gameData.getPlayers().get(1).getScore());
			if (gameData.getNumOfPlayers() > 2) {
				mScoreTubeView_yellow.setScore(gameData.getPlayers().get(2)
						.getScore());
			}
		}
	}

	public void onClickStartBtn(View target) {
		soundPool.playClick();
		finish();
		gameData.setActualPlayer(nextPlayer);
		Intent intent = new Intent(this, cz.nkgroup.LoteryActivity.class);
		startActivity(intent);
		

	}

	@Override
	protected Class getClazz() {
		return ScoreActivity.class;
	}

}
