package com.hungry_bubbles;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Manages general application information and game statistics. 
 * 
 * @author Timothy Heard, Shaun DeVos, John O'Brien, Mustafa Al Salihi
 */
public class AppInfo extends Application
{
	public static final int MAX_RADIUS = 125;
	public static final int MIN_RADIUS = 10; 
	
	public static final int PLAYER_STARTING_RADIUS = 30;
	public static final int PLAYER_TARGET_RADIUS = 100; 
	public static final int PLAYER_STARTING_DIRECTION = 0;

	public static final int MAX_BUBBLES = 8;
	
	public static final int VIRTUAL_PADDING_AMOUNT = MAX_RADIUS * 2;
	
	private static final String GAME_DATA = "GAME_DATA";
	private static final String WINS = "WINS";
	private static final String TOTAL_GAMES = "TOTAL_GAMES";
	
	// Used to persist game statistics when the application is terminated as 
	// well as to retrieve any statistics from previous games when the 
	// application starts up
	private SharedPreferences gameData;
	
	// Keeps track of the total number of games won as well as the total number
	// of games played
	int totalWins, totalGames;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		gameData = getSharedPreferences(GAME_DATA, MODE_PRIVATE);
	    totalWins = gameData.getInt(WINS, 0);
	    totalGames = gameData.getInt(TOTAL_GAMES, 0);
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		savePersistentData();
	}
	
	public void endGame(boolean win)
	{
        String msg;
		if(win)
		{
            msg = "user won";
			totalWins++;
		}
        else
        {
            msg = "user lost";
        }

		totalGames++;

		savePersistentData();

        EncodingSchema schema = new LowerCaseAlphaEncoder();

        // TODO: Use constants instead of hard-coded build version
        // TODO: Check the input validity
        Intent encodedIntent = schema.encode(message, 8).get(0);
        encodedIntent.setAction(EncodingUtils.RECEIVER_COVERT_MESSAGE_ACTION);
        startService(encodedIntent);
	}

	public int getTotalWins()
	{
		return totalWins;
	}
	
	public int getTotalGames()
	{
		return totalGames;
	}
	
	private void savePersistentData()
	{
		SharedPreferences.Editor editor = gameData.edit();
		editor.putInt(TOTAL_GAMES, totalGames);
		editor.putInt(WINS, totalWins);

		editor.commit();
	}
}
