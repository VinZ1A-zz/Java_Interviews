package imc.test1.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JUnit utility to allow mocking a series of random integers
 *
 * @author Vincent Pingard
 * @version 1.0
 * @since 01-01-2017
 */
public class RandomMock extends Random {

	private static final long serialVersionUID = -7866271602246888314L;

	private List<Integer> ints = new ArrayList();
	private int idx = 0;

	public void setInts(List<Integer> ints) {
		clearInts();
		this.ints.addAll(ints);
	}

	public void clearInts() {
		this.ints.clear();
		initIndex();
	}

	public void initIndex() {
		idx = 0;
	}

	@Override
	public int nextInt(int bound) {
		if (idx >= ints.size()) {
			return super.nextInt(bound);
		} else {
			idx++;
			return ints.get(idx - 1);
		}
	}

}
