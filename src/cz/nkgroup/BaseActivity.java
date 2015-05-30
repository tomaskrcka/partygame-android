package cz.nkgroup;

import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.SoundDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public abstract class BaseActivity extends Activity {

	protected static final int DIALOG_SHOW_BACK = 10;

	public void onBackPressed() {
		showDialog(DIALOG_SHOW_BACK);
	}

	protected abstract Class getClazz();
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SHOW_BACK: {
			return new AlertDialog.Builder(this).setTitle(R.string.pauseGame)
					.setMessage(R.string.backToMainMenu).setCancelable(true)
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									BaseActivity.this.finish();
									Intent intent = new Intent(
											BaseActivity.this,
											cz.nkgroup.MainActivity.class);
									GameDataDb.getInstance().getGameData()
											.setResumeActivity(
													getClazz());
									startActivity(intent);
								}
							}).setNegativeButton(R.string.no,
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

}
