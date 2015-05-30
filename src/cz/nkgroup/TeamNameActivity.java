package cz.nkgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;
import cz.nkgroup.vo.PlayerVO;

public class TeamNameActivity extends BaseActivity {

	private EditText mTeamnameEdTxt1;
	private EditText mTeamnameEdTxt2;
	private EditText mTeamnameEdTxt3;
	private ImageView mTeamnameTxt;
	private GameData gameData;

	private SoundDb soundPool;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamname_layout);
		prepareUI();

		gameData = GameDataDb.getInstance().getGameData();

		if (gameData.getNumOfPlayers() == 2) {
			mTeamnameEdTxt3.setVisibility(View.INVISIBLE);
		}
		String defaultName = getString(R.string.team);
		if (gameData.isPlayers()) {
			// mTeamnameTxt.setText(R.string.playername);
			defaultName = getString(R.string.player);
		}

		mTeamnameEdTxt1.setText(defaultName + " 1");
		mTeamnameEdTxt2.setText(defaultName + " 2");
		mTeamnameEdTxt3.setText(defaultName + " 3");
		
		soundPool = SoundDb.getInstance();	

	}

	/*
	 * public void onBackPressed() { finish(); Intent intent = new Intent(this,
	 * cz.nkgroup.PlayerActivity.class); startActivity(intent); }
	 */

	public void onClickCancelBtn(View target) {
		// finish();
	}

	public void onClickStartBtn(View target) {
		soundPool.playClick();
		finish();
		gameData.addPlayer(new PlayerVO(
				mTeamnameEdTxt1.getText().toString()));
		gameData.addPlayer(new PlayerVO(
				mTeamnameEdTxt2.getText().toString()));
		gameData.addPlayer(new PlayerVO(
				mTeamnameEdTxt3.getText().toString()));

		gameData.setActualPlayer(null);
		
		Intent intent = new Intent(this, cz.nkgroup.ScoreActivity.class);
		startActivity(intent);
	}

	private void prepareUI() {
		mTeamnameEdTxt1 = (EditText) this.findViewById(R.id.teamname_edtxt_1);
		mTeamnameEdTxt2 = (EditText) this.findViewById(R.id.teamname_edtxt_2);
		mTeamnameEdTxt3 = (EditText) this.findViewById(R.id.teamname_edtxt_3);
		/*mTeamnameTxt = (ImageView) this
				.findViewById(R.id.teamname_txt_teamname);*/
	}

	@Override
	protected Class getClazz() {
		return TeamNameActivity.class;
	}

}
