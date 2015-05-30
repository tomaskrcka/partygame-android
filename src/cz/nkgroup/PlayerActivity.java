package cz.nkgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;

public class PlayerActivity extends BaseActivity {
	private GameData gameData;

	private SoundDb soundPool;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playermenu_layout);
		gameData = GameDataDb.getInstance().getGameData();
		soundPool = SoundDb.getInstance();
	}

	public void onThreePlayersBtn(View target) {
		nextActivity(3, true);
	}

	public void onTwoTeamsBtn(View target) {
		nextActivity(2, false);
	}

	public void onThreeTeamsBtn(View target) {
		nextActivity(3, false);
	}
	
	private void nextActivity(Integer numOfPlayers, boolean player) {
		soundPool.playClick();
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.TeamNameActivity.class);
		gameData.setNumOfPlayers(numOfPlayers);
		gameData.setPlayers(player);
		startActivity(intent);
	}

	@Override
	protected Class getClazz() {
		return PlayerActivity.class;
	}

}
