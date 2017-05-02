package imc.test3;

import imc.test3.engine.Game;

//@formatter:off
/**
* Rock-Paper-Scissors facade launcher.
* 
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class RockPaperScissors {

	public static void main(String... args) {

		Game game = new Game(null);
		game.start(null);

	}

}
