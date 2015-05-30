package cz.nkgroup.objdb;

import cz.nkgroup.vo.GameData;

public class GameDataDb {

	private static GameDataDb instance = null;

	private GameData gameData;

	private GameDataDb() {
		gameData = new GameData();
	}

	public static GameDataDb getInstance() {
		if (instance == null) {
			instance = new GameDataDb();
		}

		return instance;
	}

	public GameData getGameData() {
		return gameData;
	}

}
