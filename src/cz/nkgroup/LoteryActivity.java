package cz.nkgroup;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import cz.nkgroup.vo.GameData;
import cz.nkgroup.vo.GameData.GAMETYPE;

public class LoteryActivity extends BaseActivity {
	private ImageView card1;
	private ImageView card2;
	private ImageView card3;
	private ImageView card11;
	private ImageView card21;
	private ImageView card31;

	private GameData gameData;
	
	private SoundDb soundPool;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lotery_layout);
		prepareUI();
		gameData = GameDataDb.getInstance().getGameData();
		changeGUI();
		soundPool = new SoundDb(this.getApplicationContext());		
	}

	private void prepareUI() {
		card1 = (ImageView) findViewById(R.id.lotery_img_card1);
		card2 = (ImageView) findViewById(R.id.lotery_img_card2);
		card3 = (ImageView) findViewById(R.id.lotery_img_card3);
		card11 = (ImageView) findViewById(R.id.lotery_img_card11);
		card21 = (ImageView) findViewById(R.id.lotery_img_card21);
		card31 = (ImageView) findViewById(R.id.lotery_img_card31);
	}
	
	private void changeGUI() {
		if (gameData.getActualPlayer().getNumOfSameJobs() > 0) {
			if (gameData.getActualPlayer().getPrevJob() == GAMETYPE.MIME) {
				card1.setVisibility(View.INVISIBLE);
				card11.setVisibility(View.INVISIBLE);
			} else {
				card2.setVisibility(View.INVISIBLE);
				card21.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	public void onClickCard(View target) {
		finish();
		Intent intent = new Intent(this, cz.nkgroup.JobActivity.class);
		
		gameData.setPoints(1);
		gameData.setRandom(false);
		
		Random r = new Random();
		int i = r.nextInt(2);
		
		switch (target.getId()) {
		case R.id.lotery_img_card1:
			gameData.setGametype(GAMETYPE.MIME);
			break;

		case R.id.lotery_img_card2:
			gameData.setGametype(GAMETYPE.ART);
			
			break;
			
		case R.id.lotery_img_card3:
			gameData.setGametype(GameData.GAMETYPE.values()[i]);
			gameData.setPoints(3);
			gameData.setRandom(true);
			break;
			
		case R.id.lotery_img_card11:
			gameData.setGametype(GAMETYPE.MIME);
			gameData.setPoints(2);
			break;

		case R.id.lotery_img_card21:
			gameData.setGametype(GAMETYPE.ART);
			gameData.setPoints(2);
			break;
			
		case R.id.lotery_img_card31:
			gameData.setGametype(GameData.GAMETYPE.values()[i]);
			gameData.setPoints(4);
			gameData.setRandom(true);
			break;
		}
		if ((target.getId() != R.id.lotery_img_card3) && (target.getId() != R.id.lotery_img_card31))
			gameData.getActualPlayer().setPrevJob(gameData.getGametype());
		
		soundPool.playClick();
		startActivity(intent);
	}
	
	@Override
	protected Class getClazz() {
		return LoteryActivity.class;
	}

}
