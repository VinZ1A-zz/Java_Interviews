package imc.test3.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import imc.test3.config.Config;
import imc.test3.engine.Player.Action;
import imc.test3.engine.Score.Results;
import imc.test3.utils.Utils;

//@formatter:off
/**
* Famous Rock-Paper-Scissors game
* Contains several features:
*   - file driven settings
*   - handling many nb/type of players
*   - powerful computation of scores
*   - almost as good as Clash of Clans
*
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class Game {

	Config config;
	Rules rules;
	Scanner reader; // can be mocked
	Random rnd = new Random(); // can be mocked

	/**
	 * Constructor. Will initialize configuration
	 * 
	 * @param resourceFolder(optional)
	 *            Resource location (externalized so it can be mocked)
	 */
	public Game(String resourceFolder) {
		init(resourceFolder);
	}

	private void init(String resourceFolder) {
		config = new Config(resourceFolder);
		rules = new Rules();
	}

	/**
	 * Very high level game launcher: Plays the game a given number of times
	 * 
	 * @param rnd(optional)
	 *            Random generator (externalized so it can be mocked)
	 * @return Map<Integer, Results> Returns Results for each player ID.
	 */
	public Map<Integer, Results> start(Random rnd) {
		reader = new Scanner(System.in);
		if (rnd != null)
			this.rnd = rnd;
		rules.display(config);

		Map<Integer, Results> lastResults;
		do {
			config.nbGames = Utils.getIntFromUser("How many games? ", 1, Integer.MAX_VALUE, reader);
			lastResults = playGame();
		} while (playAgain(reader));

		System.out.println("\nGoodbye!");
		return lastResults;
	}

	//@formatter:off
	/**
	 * The Core steps of a given game. For each turn:
	 *   - gets player Action 
	 *   - computes turn score
	 *   - merge with global score
	 *  Determines final winner after the last turn.
	 */
	//@formatter:on
	private Map<Integer, Results> playGame() {
		System.out.println();

		int turnNb = 1;
		// pre-fill empty stats
		Map<Integer, Results> globResultsPerPlayer = Score.initResultsPerPlayer(config.getPlayers());

		// loop on every turn
		do {
			// Create a new Action per player
			Map<Integer, Action> actionsPerPlayer = new HashMap<>();
			for (Player p : config.getPlayers()) {
				Action action = new Action(turnNb, p.makeChoice(config, reader, rnd));
				actionsPerPlayer.put(p.getId(), action);
			}

			System.out.println("\n******* Round " + turnNb + " results! ***********");
			// compute actions and get results
			Map<Integer, Results> resultsPerPlayer = Score.computeActions(actionsPerPlayer, config);
			// merge with global Results
			Score.mergeWithGlobal(resultsPerPlayer, globResultsPerPlayer, turnNb);
			Score.displayOngoingResults(globResultsPerPlayer, config);

			turnNb++;
		} while (turnNb <= config.nbGames);

		System.out.println("\n********* FINAL RESULTS !! ************");
		List<Player> winners = Score.getWinners(globResultsPerPlayer, config);
		if (winners.size() == config.getPlayers().size()) {
			System.out.println("It's a tie!");
		} else {
			System.out.print("Congrats to ");
			StringBuilder allPlayers = new StringBuilder();
			for (Player p : winners) {
				allPlayers.append(p.getName() + ", ");
			}
			System.out.println(allPlayers.substring(0, allPlayers.length() - 2) + "!!");
		}

		System.out.println("\n********* End of game ***********");
		return globResultsPerPlayer;
	}

	public boolean playAgain(Scanner reader) {
		System.out.print("Do you want to play again? ");
		String userInput = reader.next();
		userInput = userInput.toUpperCase();
		return userInput.charAt(0) == 'Y';
	}

}
