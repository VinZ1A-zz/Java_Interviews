package imc.test3.engine;

import imc.test3.config.Config;

//@formatter:off
/**
* Display the Rules at the beginning of the game:
*   - Available symbols
*   - For every symbol, which other symbol(s) is weaker
*   - How to play
*   - Who are the players
*
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class Rules {

	public void display(Config config) {

		System.out.println("**** Game Rules *****");
		System.out.print("Here are the symbols: ");
		StringBuilder allSymbs = new StringBuilder();
		for (Symbol sym : config.getSymbols()) {
			allSymbs.append(sym.getName() + ", ");
		}
		System.out.println(allSymbs.substring(0, allSymbs.length() - 2));

		for (Symbol sym : config.getSymbols()) {
			System.out.print("   - " + sym.getName() + "(" + sym.getId() + ") wins against ");
			allSymbs = new StringBuilder();
			for (Integer weakerSymId : sym.getWins()) {
				allSymbs.append(config.getSymbolFromId(weakerSymId).getName() + ", ");
			}
			System.out.println(allSymbs.substring(0, allSymbs.length() - 2));
		}

		// what can be entered (symbols) or digits (1..3)
		System.out.println("You may use the Id (eg. 1 to " + config.getSymbols().size() + ") when playing.");

		// good luck Vinz(human(s)) and Marcel(bots)
		System.out.print("Good luck, ");
		allSymbs = new StringBuilder();
		for (Player p : config.getPlayers()) {
			allSymbs.append(p.toStringLight() + ", ");
		}
		System.out.println(allSymbs.substring(0, allSymbs.length() - 2) + "!");

		System.out.println();
	}

}
