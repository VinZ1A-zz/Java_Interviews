package imc.test3.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import imc.test3.config.Config;
import imc.test3.engine.Player.Action;
import imc.test3.utils.Utils;

//@formatter:off
/**
* A helper class used to store, compute, merge, and display Scores.
* 
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class Score {

	public static class Results implements Comparable<Results> {
		int wins;
		int losses;
		int ties;
		double percentWon = -1;

		public Results() {
		}

		public Results(int wins, int losses, int ties, double percentWon) {
			this.wins = wins;
			this.losses = losses;
			this.ties = ties;
			this.percentWon = percentWon;
		}

		public int getWins() {
			return wins;
		}

		public int getLosses() {
			return losses;
		}

		public int getTies() {
			return ties;
		}

		public double getPercentWon() {
			return percentWon;
		}

		@Override
		public int compareTo(Results o) {
			if (wins == o.wins)
				return 0;
			else if (wins < o.wins)
				return -1;
			else
				return 1;
		}
	}

	/**
	 * Initializes empty result data for each player
	 * 
	 * @param players
	 *            All players
	 * @return Map<Integer, Results> Returns empty results
	 */
	static Map<Integer, Results> initResultsPerPlayer(List<Player> players) {
		Map<Integer, Results> globResultsPerPlayer = new HashMap<>();
		for (Player p : players) {

			Results res = new Results();
			globResultsPerPlayer.put(p.getId(), res);
		}
		return globResultsPerPlayer;
	}

	/**
	 * Once each player has played, this will compute results, eg. determine
	 * Wins, losses and ties. Also, displays the results on the console screen.
	 * 
	 * I am NOT implementing rules described here:
	 * http://stephan.sugarmotor.org/2010/04/rock-paper-scissors-for-three-people/
	 * Instead: comparing each player with all the others hence, could have more
	 * than 1 win point per game when more than 2 people are playing
	 * 
	 * @param actionsPerPlayer
	 *            Action of each player
	 * @return Map<Integer, Results> Returns results of this current turn
	 */
	static Map<Integer, Results> computeActions(Map<Integer, Action> actionsPerPlayer, Config config) {
		Map<Integer, Results> resultsPerPlayer = new HashMap<>();
		// Display the Action of each Player
		for (Entry<Integer, Action> id1ToAction : actionsPerPlayer.entrySet()) {
			Player p = config.getPlayerFromId(id1ToAction.getKey());
			Action a = id1ToAction.getValue();
			System.out.println(p.getName() + " has chosen " + config.getSymbolFromId(a.choice).getName());
			// pre-fill empty turn results
			Results res = new Results();
			resultsPerPlayer.put(id1ToAction.getKey(), res);
		}
		for (Entry<Integer, Action> id1ToAction : actionsPerPlayer.entrySet()) {
			for (Entry<Integer, Action> id2ToAction : actionsPerPlayer.entrySet()) {
				if (id2ToAction.getKey().compareTo(id1ToAction.getKey()) <= 0)
					continue;
				Player p1 = config.getPlayerFromId(id1ToAction.getKey());
				Player p2 = config.getPlayerFromId(id2ToAction.getKey());
				Action a1 = id1ToAction.getValue();
				Action a2 = id2ToAction.getValue();
				System.out.print(p1.getName() + "(" + config.getSymbolFromId(a1.choice).getName() + ") " + //
						"vs " + p2.getName() + "(" + config.getSymbolFromId(a2.choice).getName() + ") : ");
				if (a1.choice == a2.choice) {
					System.out.println("TIE");
					resultsPerPlayer.get(p1.getId()).ties++;
					resultsPerPlayer.get(p2.getId()).ties++;
				} else {
					if (config.getSymbolFromId(a1.choice).winsAgainst(a2.choice)) {
						System.out.println(p1.getName().toUpperCase() + " WINS");
						resultsPerPlayer.get(p1.getId()).wins++;
						resultsPerPlayer.get(p2.getId()).losses++;
					} else {
						System.out.println(p2.getName().toUpperCase() + " WINS");
						resultsPerPlayer.get(p1.getId()).losses++;
						resultsPerPlayer.get(p2.getId()).wins++;
					}
				}
			}
		}
		return resultsPerPlayer;
	}

	/**
	 * Merges the results from last turn with the overall Score
	 * 
	 * @param resultsPerPlayer
	 *            Last Turn Results of each player
	 * @Param globResPerPlayer (in/out) Results updated for each Player
	 */
	static void mergeWithGlobal(Map<Integer, Results> resultsPerPlayer, Map<Integer, Results> globResPerPlayer,
			int roundNb) {
		for (int pId : resultsPerPlayer.keySet()) {
			Results res = globResPerPlayer.get(pId);
			res.wins += resultsPerPlayer.get(pId).wins;
			res.losses += resultsPerPlayer.get(pId).losses;
			res.ties += resultsPerPlayer.get(pId).ties;
			// Ties are counted as a half-win
			res.percentWon = ((double) res.wins + ((double) res.ties / 2)) / roundNb;
		}
	}

	/**
	 * Display results (whether they are ongoing or final). Results are sorted,
	 * highest Wins first.
	 * 
	 * @param results
	 *            Last Turn Results of each player
	 */
	static void displayOngoingResults(Map<Integer, Results> results, Config config) {
		Map<Integer, Results> sortedResults = Utils.sortByValue(results);

		System.out.printf("|  %15s  |  %6s  |  %6s  |  %6s  |   %9s  |", "NAME", "Wins", "Losses", "Ties",
				"Percent Won");
		System.out.println();
		for (Entry<Integer, Results> id1ToResult : sortedResults.entrySet()) {
			String name = config.getPlayerFromId(id1ToResult.getKey()).getName();
			int wins = id1ToResult.getValue().wins;
			int losses = id1ToResult.getValue().losses;
			int ties = id1ToResult.getValue().ties;
			double percentWon = id1ToResult.getValue().percentWon;
			System.out.printf("|  %15s  |  %6d  |  %6d  |  %6d  |   %10.2f   |",
					name.substring(0, Math.min(15, name.length())), wins, losses, ties, percentWon);
			System.out.println();
		}
	}

	/**
	 * Gets the list of Winners (could be several Winners when more than 2
	 * players and same # of wins)
	 * 
	 * @param globResPerPlayer
	 *            Global Results of each player
	 * @return List<Player> Returns list of Winners
	 */
	static List<Player> getWinners(Map<Integer, Results> globResPerPlayer, Config config) {
		List<Player> winners = new ArrayList<>();

		Map<Integer, Results> sortedResults = Utils.sortByValue(globResPerPlayer);

		int maxScore = -1;
		for (Entry<Integer, Results> playerIdToRes : sortedResults.entrySet()) {
			int wins = playerIdToRes.getValue().wins;
			if (wins > maxScore) {
				maxScore = wins;
			}
			if (wins == maxScore) {
				winners.add(config.getPlayerFromId(playerIdToRes.getKey()));
			}
		}
		return winners;
	}

}
