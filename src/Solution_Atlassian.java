import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Solution_Atlassian {

	static final boolean _debug = "true".equals(System.getProperties().get("debug"));

	public static void main(String[] args) {

		if (args.length != 0) {
			System.err.println("in debug with debug = " + System.getProperties().get("debug"));
			// _debug = true; // set as final
			// _fromEclipse = true;
			String aInput = "";
			for (String data : getData()) {
				aInput += data + "\r\n";
			}
			System.setIn(new ByteArrayInputStream(aInput.getBytes()));
		}

		doIt4();

	}

	// debug method - discard!
	static private Vector<String> getData() {
		Vector<String> aInput = new Vector<String>();

		// exercise 1
		// aInput.add("8 ");
		// aInput.add("1 3 4 5 3 4 5 6");
		// aInput.add("4");
		// aInput.add("3 4 5 6");

		// exercise 2
		// aInput.add("11");
		// aInput.add("2");

		// exercise 3
		// aInput.add("779287"); // atlassian
		// aInput.add("894"); // 196: 400 / 894:2415
		// aInput.add("151"); // test with 0

		// exercise 4
		// aInput.add("PMLPMMMLPMLPMML");
		aInput.add("PPMLPMMMLPMLPPPMMLPLPLPLPLPLPLPLPLPLPLPLPLPLPLPLPLML"); // L
																			// before
																			// L
																			// does
																			// nothing

		return aInput;
	}

	// see Atlassian exercise # 4.jpg
	private static void doIt4() {
		Scanner scan = new Scanner(System.in);

		String commands = scan.next();
		// debugln(commands);
		String res = execute(commands.toUpperCase());
		println(res);

	}

	private static String execute(String str) {
		int len = str.length();
		Map<Integer, Integer> countPerRow = new HashMap<>();

		// init map
		for (int i = 0; i <= 9; i++) {
			countPerRow.put(i, 0);
		}

		int cur = 0;
		int pickupIdx;
		// grab nb of individual pickup points (ignoring consecutive pickups)
		List<Integer> pickupPoints = new ArrayList<>();
		do {
			pickupIdx = str.indexOf('P', cur);

			while (pickupIdx >= 0 && pickupIdx < len && str.charAt(pickupIdx) == 'P') {
				pickupIdx++;
			}
			cur = pickupIdx;
			if (pickupIdx != -1)
				pickupPoints.add(pickupIdx - 1);
		} while (pickupIdx != -1);

		debugln(pickupPoints);

		// decode each instruction: suite of 'M' until first 'L', ignore
		// following any 'L'
		for (int pickPt : pickupPoints) {
			pickPt++;
			int nbOfMoves = 0;
			while (pickPt < len && str.charAt(pickPt) == 'M') {
				nbOfMoves++;
				pickPt++;
			}
			if (nbOfMoves > 9) // cap to 9
				nbOfMoves = 9;
			debugln("from pickPt " + pickPt + " , nb of moves " + nbOfMoves);
			// do we have a lower afterwards?
			if (pickPt < len && str.charAt(pickPt) == 'L') {
				int curRowVal = countPerRow.get(nbOfMoves);
				if (curRowVal == 15) {
					// xxxx already full - do nothing BUT block is still there !
					// detect further moves! TODO
				}
				countPerRow.put(nbOfMoves, countPerRow.get(nbOfMoves) + 1);
			}
		}

		debugln(countPerRow);

		// final display
		StringBuffer res = new StringBuffer();
		for (int rowVal : countPerRow.values()) {
			res.append(Integer.toHexString(rowVal).toUpperCase());
		}

		return res.toString();
	}

	// convert to base 7
	private static void doIt3() {
		Scanner scan = new Scanner(System.in);

		int toConv = scan.nextInt();

		String conv = convertToBase7NoLog(toConv);
		debugln("conv " + conv);
		conv = convertToAltassian(conv);
		debugln("conv to Atlassian " + conv);

		scan.close();
	}

	private static String convertToAltassian(String str) {
		char[] convChars = new char[] { '0', 'a', 't', 'l', 's', 'i', 'n' };
		StringBuffer res = new StringBuffer();
		try {
			for (char c : str.toCharArray()) {
				int val = Integer.parseInt(Character.toString(c));
				if (val > 6) {
					throw new NumberFormatException("input String should be composed of digits from 0 to 6 only");
				}
				res.append(convChars[val]);
			}
		} catch (NumberFormatException e) {
			throw e;
		}
		return res.toString();
	}

	private static String convertToBase7NoLog(int toConv) {
		StringBuffer res = new StringBuffer();
		int div;
		do {
			div = toConv / 7;
			int remainder = toConv - div * 7;
			debugln("div " + div + " ; rem: " + remainder);
			res.append(remainder);
			toConv = div;
		} while (div > 0);
		return res.reverse().toString();
	}

	private static String convertToBase7(int toConv) {
		double highestPow = Math.ceil((Math.log(toConv) / Math.log(7)));
		debugln("highestPow " + highestPow);

		StringBuffer conv = new StringBuffer();
		for (double pow = highestPow; pow >= 0; pow--) {
			int sevPow = (int) Math.pow(7, pow);
			int div = toConv / sevPow;
			int remainder = toConv - (div * sevPow);
			if (div == 0 && conv.length() == 0) {
				continue;
			}
			conv.append(div);

			debugln("remainder " + remainder);
			debugln("div " + div);
			toConv = remainder;
		}
		return conv.toString();
	}

	private static void doIt2() {
		Scanner scan = new Scanner(System.in);

		String nbToRead = scan.nextLine();
		int repeat = scan.nextInt();

		for (int i = 0; i < repeat; i++) {
			String read = lookAndSay(nbToRead);
			nbToRead = read;
		}
		println(nbToRead);

		scan.close();
	}

	private static void doIt1() {
		Scanner scan = new Scanner(System.in);

		try {
			int l1Size = scan.nextInt();
			List<Integer> orig = new ArrayList<Integer>();

			for (int i = 0; i < l1Size; i++) {
				orig.add(scan.nextInt());
			}
			List<Integer> sub = new ArrayList<Integer>();
			int l2Size = scan.nextInt();
			for (int i = 0; i < l2Size; i++) {
				sub.add(scan.nextInt());
			}
			scan.close();

			debugln(doesContain(orig, sub));
		} catch (InputMismatchException e) {
			println(-1);
		}

	}

	// "111221") return 312211
	public static String lookAndSay(String str) {
		if (str == null)
			return null;

		StringBuffer res = new StringBuffer();

		char prev = str.charAt(0);
		int count = 1;
		for (int idx = 1; idx < str.length(); idx++) {
			char cur = str.charAt(idx);
			if (prev == cur) {
				count++;
			} else {
				debugln("count " + count + " ; prev " + prev);
				res.append(String.valueOf(count) + prev);
				count = 1;
			}
			prev = cur;
		}
		debugln("FINAL - count " + count + " ; prev " + prev);
		res.append(String.valueOf(count) + prev);

		return res.toString();
	}

	// KW: isSubset
	public static int doesContain(List<Integer> orig, List<Integer> sub) {

		for (int iOrig = 0; iOrig < orig.size(); iOrig++) {
			int idxO = iOrig;
			debugln("**** starting comp at " + iOrig);
			for (int iSub = 0; iSub < sub.size(); iSub++) {
				int valS = sub.get(iSub);
				int valO = orig.get(idxO);
				debugln("comp O:" + valO + " and S:" + valS);
				if (valS != valO) {
					debugln("getting out at " + idxO);
					if (idxO - iOrig >= 2) {
						iOrig = idxO - 1;
					}
					break;
				}
				if (iSub == sub.size() - 1) {
					return idxO - sub.size() + 1;
				}
				idxO++;
			}

		}

		return -1;
	}

	public static int doesContain_SUPER_MOCHE(List<Integer> orig, List<Integer> sub) {

		int idxSub = 0;
		int idxOrig;
		for (idxOrig = 0; idxOrig < orig.size(); idxOrig++) {
			debugln("at " + idxOrig + " and sub " + idxSub);

			if (idxSub < sub.size()) {
				debugln(sub.get(idxSub) + " compared to " + orig.get(idxOrig));
				if (sub.get(idxSub) != orig.get(idxOrig)) {
					idxSub = 0;
					if (sub.get(idxSub) == orig.get(idxOrig)) {
						idxOrig--;
					}
				} else {
					idxSub++;
				}
			}
			if (idxSub == sub.size()) {
				break;
			}
		}

		if (idxSub == sub.size()) {
			return (idxOrig - sub.size() + 1);
		}
		return -1;
	}

	private static void debug(Object obj) {
		if (_debug) {
			System.err.print(obj.toString());
		}
	}

	private static void debugln(Object obj) {
		if (_debug) {
			System.err.println(obj.toString());
		}
	}

	private static void print(Object obj) {
		System.out.print(obj.toString());
	}

	private static void println(Object obj) {
		System.out.println(obj.toString());
	}

}
