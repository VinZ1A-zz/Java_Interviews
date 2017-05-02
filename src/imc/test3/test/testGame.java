package imc.test3.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import imc.test1.test.RandomMock;
import imc.test3.engine.Game;
import imc.test3.engine.Score.Results;

//@formatter:off
/**
* JUNIT of the game, using several mocks:
*   - Random mock
*   - Input stream mock 
*  The test folder contains its own players/symbols setup.
*   
* Individual unit tests could be added here.
*
* @author Vincent Pingard
* @version 1.0
* @since 01-01-2017
*/
//@formatter:on
public class testGame {

	static Game game;
	/** using a pre-defined random sequence */
	static RandomMock rnd; // from test 1

	@BeforeClass
	public static void toDoBeforeAllTest() {
		System.err.println("preparing ALL tests");
		rnd = new RandomMock();
		game = new Game("test");
	}

	@After
	public void toDoAfterEachTest() {
		System.err.println("cleaning-up test");
	}

	@Test
	public void testCompleteGame() {
		String inputStr = "";
		Vector<String> aInput = new Vector<String>();
		aInput.add("4");
		aInput.add("1");
		aInput.add("2");
		aInput.add("1");
		aInput.add("3");
		aInput.add("N ");

		rnd.setInts(Arrays.asList(//
				0, 2, //
				1, 2, //
				2, 0, //
				1, 1));

		for (String data : aInput) {
			inputStr += data + "\r\n";
		}
		System.setIn(new ByteArrayInputStream(inputStr.getBytes()));

		Map<Integer, Results> expectedResults = new HashMap<>();
		expectedResults.put(1, new Results(4, 1, 3, 1.375));
		expectedResults.put(2, new Results(1, 4, 3, 0.625));
		expectedResults.put(3, new Results(3, 3, 2, 1.00));
		Map<Integer, Results> lastResults = game.start(rnd);

		assertEquals("Invalid number results returned", expectedResults.size(), lastResults.size());
		for (int playerId : lastResults.keySet()) {
			Results cur = lastResults.get(playerId);
			Results exp = expectedResults.get(playerId);
			assertEquals("Invalid wins for playerId " + playerId, exp.getWins(), cur.getWins());
			assertEquals("Invalid losses for playerId " + playerId, exp.getLosses(), cur.getLosses());
			assertEquals("Invalid ties for playerId " + playerId, exp.getTies(), cur.getTies());
			assertEquals("Invalid percent won for playerId " + playerId, exp.getPercentWon(), cur.getPercentWon(),
					0.0001); // fuzzy match
		}
	}

}
