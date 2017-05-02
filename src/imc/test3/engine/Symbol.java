package imc.test3.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * POJO for an Symbol (eg. Rock, Paper,...)
 * 
 */
public class Symbol {
	private int id;
	private String name;
	/** list of weaker symbols */
	private Set<Integer> wins = new HashSet<>();

	public Symbol(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/** adds another weaker symbol */
	public void addWin(int id) {
		wins.add(id);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getWins() {
		return new ArrayList<Integer>(wins);
	}

	/** returns True if this Symbol would win against the given symb ID */
	public boolean winsAgainst(int symId) {
		return wins.contains(symId);
	}

	@Override
	public String toString() {
		return name + "(" + id + "), wins: " + wins;
	}
}