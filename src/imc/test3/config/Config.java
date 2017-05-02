package imc.test3.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import imc.test3.RockPaperScissors;
import imc.test3.engine.Player;
import imc.test3.engine.Symbol;
import imc.test3.utils.Utils;

//@formatter:off
/**
 * Handles Game config:
 *   - symbols parsing and storage
 *   - players data parsint and storage
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
//@formatter:on
public class Config {

	/** nb of successive games */
	public int nbGames;
	/** default location of resources xml,csv... resources */
	private String resourceFolder = "resources";

	/**
	 * Constructor. Will build Symbols and Players data.
	 * 
	 * @param resourceFolder
	 *            Resource location (externalized so it can be mocked)
	 */
	public Config(String resourceFolder) {
		if (resourceFolder != null) {
			this.resourceFolder = resourceFolder;
		}
		buildSymbols();
		buildPlayers();
	}

	/** ************* Handling of list of Symbols *********************** */
	private Map<Integer, Symbol> symbols = new HashMap<>();

	public List<Symbol> getSymbols() {
		return new ArrayList<Symbol>(symbols.values());
	}

	public Symbol getSymbolFromId(int id) {
		return symbols.get(id);
	}

	/**
	 * This method parses the resource file and builds a list of Symbols. This
	 * will be helpful to determine which symbols exist and which symbol is
	 * weaker/stronger than other ones.
	 */
	public void buildSymbols() {

		// Example of content
		// ,Rock,Paper,Scissors
		// Rock,,1,0
		// Paper,,,1
		// Scissors,,,
		List<String> rawSymbols = Utils.getFileContent(this, "symbols.csv", "../" + resourceFolder + "/");

		for (int idx = 0; idx < rawSymbols.size(); idx++) {
			String[] cells = rawSymbols.get(idx).split(",");
			if (idx == 0) { // header
				for (int symbolNb = 1; symbolNb < cells.length; symbolNb++) {
					Symbol symb = new Symbol(symbolNb, cells[symbolNb]);
					symbols.put(symbolNb, symb);
				}
			} else { // compute wins
				for (int symbolNb = 1; symbolNb < cells.length; symbolNb++) {
					if (cells[symbolNb].toUpperCase().equals("W")) {
						symbols.get(symbolNb).addWin(idx);
					}
				}
			}
		}
	}

	/**
	 * ********************* Handling list of Players ************************
	 */
	private Map<Integer, Player> players = new HashMap<>();

	/**
	 * This method parses the resource file containing Player information:
	 * number of players, names and whether they should be played by a human or
	 * by the program.
	 */
	public void buildPlayers() {

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(
					new File(Utils.getCurrentPackage(new RockPaperScissors()) + "/" + resourceFolder + "/players.xml"));
			Element rootElem = doc.getDocumentElement();
			// read each <player> content
			NodeList nodes = rootElem.getElementsByTagName("player");
			for (int i = 1; i <= nodes.getLength(); i++) {
				Element e = (Element) nodes.item(i - 1);
				Player p = new Player(i, e.getElementsByTagName("name").item(0).getTextContent(),
						Boolean.parseBoolean(e.getElementsByTagName("human").item(0).getTextContent()));
				players.put(i, p);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (players.isEmpty()) { // use default values
				// Could also throw an exception here.
				System.err.println("!! Using Default Players Values !!");
				Player p1 = new Player(1, "VinZ", true);
				Player p2 = new Player(2, "Marcel", false);
				Player p3 = new Player(3, "Roger", false);
				players.put(1, p1);
				players.put(2, p2);
				players.put(3, p3);
			}
		}

	}

	/**
	 * Retrieve all stored Player instances
	 * 
	 * @return List<Player> Returns a list of Player instances
	 */
	public List<Player> getPlayers() {
		return new ArrayList<Player>(players.values());
	}

	/**
	 * Given an ID, retrieve the corresponding stored Player instance
	 * 
	 * @param id
	 *            Id of the player
	 * @return Player Returns Player, null if not found
	 */
	public Player getPlayerFromId(int id) {
		return players.get(id);
	}

	/**
	 * Get the count of human players in the game. Note: not 'heavy' enough to
	 * be cached, but could be done.
	 */
	public int nbOfHumanPlayers() {
		int res = 0;
		for (Player p : players.values()) {
			if (p.isHuman())
				res++;
		}
		return res;
	}

}
