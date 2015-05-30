package cz.nkgroup;

import java.io.InputStream;
import java.util.Set;

import cz.nkgroup.objdb.GameDataDb;
import cz.nkgroup.objdb.Settings;
import cz.nkgroup.objdb.SoundDb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	
	private Settings settings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_layout);		
		settings = Settings.getInstance();
		prepareUI();
	}
	
	private void prepareUI() {
		((CheckBox) this.findViewById(R.id.settings_sportCB)).setChecked(false);
		((CheckBox) this.findViewById(R.id.settings_jobCB)).setChecked(false);
		((CheckBox) this.findViewById(R.id.settings_sexCB)).setChecked(false);
		
		for (Integer cat : settings.getCategories()) {
			if (cat.intValue() == Settings.SETTINGS_SPORT)
				((CheckBox) this.findViewById(R.id.settings_sportCB)).setChecked(true);
			if (cat.intValue() == Settings.SETTINGS_JOBS)
				((CheckBox) this.findViewById(R.id.settings_jobCB)).setChecked(true);
			if (cat.intValue() == Settings.SETTINGS_SEX)
				((CheckBox) this.findViewById(R.id.settings_sexCB)).setChecked(true);
		}
		
		
		((ToggleButton) this.findViewById(R.id.settings_soundBtn)).setChecked(settings.isSound());
		
		int type = settings.getGameType();
		((RadioButton) this.findViewById(R.id.settings_easyRB)).setChecked(false);
		((RadioButton) this.findViewById(R.id.settings_mediumRB)).setChecked(false);
		((RadioButton) this.findViewById(R.id.settings_hardRB)).setChecked(false);
		switch (type) {
		case Settings.SETTINGS_GAMEEASY:
			((RadioButton) this.findViewById(R.id.settings_easyRB)).setChecked(true);
			break;

		case Settings.SETTINGS_GAMEMEDIUM:
			((RadioButton) this.findViewById(R.id.settings_mediumRB)).setChecked(true);
			break;
			
		case Settings.SETTINGS_GAMEHARD:
			((RadioButton) this.findViewById(R.id.settings_hardRB)).setChecked(true);
			break;
		}
	}

	public void onBackPressed() {
		this.finish();
		Intent intent = new Intent(this, cz.nkgroup.MainActivity.class);
		startActivity(intent);
	}
	
	public void onClickSaveBtn(View target) {
		
		settings.setCategory(Settings.SETTINGS_SPORT, ((CheckBox) this.findViewById(R.id.settings_sportCB)).isChecked());
		settings.setCategory(Settings.SETTINGS_JOBS, ((CheckBox) this.findViewById(R.id.settings_jobCB)).isChecked());
		settings.setCategory(Settings.SETTINGS_SEX, ((CheckBox) this.findViewById(R.id.settings_sexCB)).isChecked());
		
		if (((RadioButton) this.findViewById(R.id.settings_easyRB)).isChecked()) {
			settings.setGameType(Settings.SETTINGS_GAMEEASY);
		} else if (((RadioButton) this.findViewById(R.id.settings_mediumRB)).isChecked()) {
			settings.setGameType(Settings.SETTINGS_GAMEMEDIUM);
		} else if (((RadioButton) this.findViewById(R.id.settings_hardRB)).isChecked()) {
			settings.setGameType(Settings.SETTINGS_GAMEHARD);
		}
		
		settings.setSound(((ToggleButton) this.findViewById(R.id.settings_soundBtn)).isChecked());
		SoundDb.getInstance().setSound(settings.isSound());
		SoundDb.getInstance().playClick();
		Intent intent = new Intent(this, cz.nkgroup.MainActivity.class);
		startActivity(intent);
	}
	
	public void onClickCancelBtn(View target) {
		SoundDb.getInstance().playClick();
		Intent intent = new Intent(this, cz.nkgroup.MainActivity.class);
		startActivity(intent);
	}
	

}
