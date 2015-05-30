package cz.nkgroup.vo;

import java.io.Serializable;

import cz.nkgroup.vo.GameData.GAMETYPE;

public class PlayerVO implements Serializable {
	private static final long serialVersionUID = -7862838492910547618L;

	private String playerName;
	private int score = 0;
	private GAMETYPE prevJob;
	private int numOfSameJobs = 0;

	public GAMETYPE getPrevJob() {
		return prevJob;
	}

	public void setPrevJob(GAMETYPE prevJob) {
		if (this.prevJob == prevJob)
			this.numOfSameJobs++; 
		else
			this.numOfSameJobs = 0;
		
		this.prevJob = prevJob;
	}

	public int getNumOfSameJobs() {
		return numOfSameJobs;
	}

	public void setNumOfSameJobs(int numOfSameJobs) {
		this.numOfSameJobs = numOfSameJobs;
	}

	public PlayerVO() {

	}

	public PlayerVO(String name) {
		this.playerName = name;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if (score < 0) {
			this.score = 0;
		} else {
			if (score > 15) {
				this.score = 15;
			} else
				this.score = score;
		}
	}

}
