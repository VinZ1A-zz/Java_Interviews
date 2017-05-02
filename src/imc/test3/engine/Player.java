package imc.test3.engine;

import java.util.Random;
import java.util.Scanner;

import imc.test3.config.Config;
import imc.test3.utils.Utils;

//@formatter:off
/**
* Basic mapping of Player data
* 
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class Player {
	private int id;
	private String name;
	private boolean isHuman;

	public Player(int id, String name, boolean isHuman) {
		this.id = id;
		this.name = name;
		this.isHuman = isHuman;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isHuman() {
		return isHuman;
	}

	@Override
	public String toString() {
		return name + "(" + id + ")/" + (isHuman ? "(H)" : "(B)");
	}

	public String toStringLight() {
		return name + (isHuman ? "(Human)" : "(Bot)");
	}

	/**
	 * Allows a Player (Human or Bot) to make a (highly strategic) choice.
	 * 
	 * @param config
	 *            Allows access to current configuration
	 * @param reader
	 *            Current Input stream
	 * @param rnd
	 *            Current random generator
	 */
	public int makeChoice(Config config, Scanner reader, Random rnd) {
		int choice;
		if (isHuman()) {
			choice = Utils.getIntFromUser(getName() + " enter your choice (1 to " + config.getSymbols().size() + "): ", //
					1, config.getSymbols().size(), reader);
			if (config.nbOfHumanPlayers() == 1) {
				System.out.println("You've chosen " + config.getSymbolFromId(choice).getName() + ".");
			}
		} else { // bot picking nb
			choice = rnd.nextInt(config.getSymbols().size()) + 1;
		}
		return choice;
	}

	/**
	 * POJO for an Action (a choice within a given turn)
	 * 
	 */
	public static class Action {
		int turnNb;
		int choice;

		Action(int turnNb, int choice) {
			this.turnNb = turnNb;
			this.choice = choice;
		}
	}
}
