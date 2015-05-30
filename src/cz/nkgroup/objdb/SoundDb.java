package cz.nkgroup.objdb;

import java.util.Random;

import cz.nkgroup.R;
import cz.nkgroup.SettingsActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundDb {

	private static SoundDb instance;

	private SoundPool soundPool;
	private int clickSound;
	private int[] winnerSounds;
	private int[] looserSounds;
	private int tickSound;
	private int bellSound;

	private boolean isSound = true;

	public static SoundDb getInstance() {
		if (instance == null)
			throw new Error("Neni initialize");

		return instance;
	}

	public SoundDb(Context context) {
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		clickSound = soundPool.load(context, R.raw.click, 0);
		winnerSounds = new int[3];
		winnerSounds[0] = soundPool.load(context, R.raw.winner1, 0);
		winnerSounds[1] = soundPool.load(context, R.raw.winner1, 0);
		winnerSounds[2] = soundPool.load(context, R.raw.winner3, 0);

		looserSounds = new int[4];
		looserSounds[0] = soundPool.load(context, R.raw.loos1, 0);
		looserSounds[1] = soundPool.load(context, R.raw.loos2, 0);
		looserSounds[2] = soundPool.load(context, R.raw.loos3, 0);
		looserSounds[3] = soundPool.load(context, R.raw.loos4, 0);

		tickSound = soundPool.load(context, R.raw.tick, 0);
		bellSound = soundPool.load(context, R.raw.bell, 0);
		instance = this;
		isSound = Settings.getInstance().isSound();
		
	}
	
	public void setSound(boolean sound) {
		this.isSound = sound;
	}

	public void playClick() {
		if (isSound)
			soundPool.play(clickSound, 1, 1, 0, 0, 1);
	}

	public void playWinner() {
		if (isSound) {
			Random r = new Random();
			soundPool.play(winnerSounds[r.nextInt(3)], 1, 1, 0, 0, 1);
		}
	}

	public void playLooser() {
		if (isSound) {
			Random r = new Random();
			soundPool.play(looserSounds[r.nextInt(4)], 1, 1, 0, 0, 1);
		}
	}

	public void playTick() {
		if (isSound)
			soundPool.play(tickSound, 1, 1, 0, 0, 1);
	}
	
	public void playBell() {
		if (isSound)
			soundPool.play(bellSound, 1, 1, 0, 0, 1);
	}

}
