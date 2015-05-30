package cz.nkgroup.vo;

import java.io.Serializable;
import java.util.ArrayList;

import cz.nkgroup.objdb.Settings;

public class GameData implements Serializable {
	private static final long serialVersionUID = -7427580543237109211L;
	public final static String gameData = "gameData";

	public enum GAMETYPE {
		ART, MIME
	};

	private ArrayList<PlayerVO> playersList = new ArrayList<PlayerVO>();

	private boolean players = false;
	private Integer numOfPlayers = 2;

	private GAMETYPE gametype;
	private String jobstring;
	
	private PlayerVO actualPlayer;

	private Class resumeActivity = null;
	
	private int lastTime;
	
	private int points;

	private boolean random;

	public int getJobTime() {
		int type = Settings.getInstance().getGameType();
		int res = 60;
		switch (type) {
		case Settings.SETTINGS_GAMEEASY:
			res = 80;
			break;
		
		case Settings.SETTINGS_GAMEMEDIUM:
			res = 60;
			break;

		case Settings.SETTINGS_GAMEHARD:
			res = 45;
			break;
		}
		
		return res;
		
	}

	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public boolean isPlayers() {
		return players;
	}

	public void setPlayers(boolean players) {
		this.players = players;
	}

	public Integer getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(Integer num) {
		this.numOfPlayers = num;
	}

	public void addPlayer(PlayerVO player) {
		this.playersList.add(player);
	}

	public PlayerVO getPlayerNumber(int playerNum) {
		return playersList.get(playerNum - 1);
	}

	public void removePlayer(PlayerVO player) {
		this.playersList.remove(player);
	}
	
	public ArrayList<PlayerVO> getPlayers() {
		 return this.playersList;
	}

	public void removeAllPlayers() {
		this.playersList.clear();
	}

	public GAMETYPE getGametype() {
		return gametype;
	}

	public void setGametype(GAMETYPE gametype) {
		this.gametype = gametype;
	}

	public String getJobstring() {
		return jobstring;
	}

	public void setJobstring(String jobstring) {
		this.jobstring = jobstring;
	}

	public void setResumeActivity(Class resumeActivity) {
		this.resumeActivity = resumeActivity;
	}

	public Class getResumeActivity() {
		return resumeActivity;
	}

	public void setActualPlayer(PlayerVO actualPlayer) {
		this.actualPlayer = actualPlayer;
	}

	public PlayerVO getActualPlayer() {
		return actualPlayer;
	}
	
	public PlayerVO nextPlayer() {
		if (actualPlayer == null) {
			return playersList.get(0);
		}
		
		int actnum = playersList.indexOf(actualPlayer);
		if (actnum == (numOfPlayers - 1)) {
			return playersList.get(0);
		}
		
		return playersList.get(actnum + 1);
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}
	
}
