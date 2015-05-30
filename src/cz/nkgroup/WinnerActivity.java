package cz.nkgroup;

import cz.nkgroup.objdb.GameDataDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinnerActivity extends Activity  {
	private TextView winnerText;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.winner_layout);
		winnerText = (TextView) this.findViewById(R.id.winner_title);
		winnerText.setText(winnerText.getText() + GameDataDb.getInstance().getGameData().getActualPlayer().getPlayerName());
		GameDataDb.getInstance().getGameData().setResumeActivity(null);
	}

	
	public void onClickBtn(View target) {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.MainActivity.class);
		startActivity(intent);
		// 
	}
	
}
