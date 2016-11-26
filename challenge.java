import java.util.*;

public class challenge {
	/* Part A */

	// find the length of the longest overlap between two strings
	public static int longestOverlap (String a, String b) {
		int current = -1;
		int length = Math.min(a.length(), b.length());
		if (length == 0) return 0;
		for (int i = 1; i <= length; i++) {
			if (a.substring(a.length() - i).equals(b.substring(0, i)) && current < i) {
				current = i;
			}
		}
		if (current == -1) return 0;
		return current;
	}

	/* Part B */

	// find the line of DNA that does not belong to the sequence
	public static String[] findWrongDNA (ArrayList<String> sequence) {
		// a 2D boolean array indicating if the head and tail of the current String could
		// be connected to another String sequence.
		boolean[][] connected = new boolean[sequence.size()][2];
		for (int i = 0; i < sequence.size(); i++) {	
			String currentSequence = sequence.get(i);
			int length = currentSequence.length();
			for (int j = 0; j < sequence.size(); j++) {
				if (j != i) {
					// the current String as read1					
					// check if the tail of the current string is connected to another read
					String comparison = sequence.get(j);
					int comparisonLength = comparison.length();
					int overlap = longestOverlap(currentSequence, comparison);
					if (overlap >= length * 0.5 && overlap >= comparisonLength * 0.5) {
						connected[i][1] = true;
						connected[j][0] = true;
						continue;
					}
					// the current String as read2
					// check if the head of the current string is connected to another read
					String comparison2 = sequence.get(j);
					int comparisonLength2 = comparison2.length();
					int overlap2 = longestOverlap(comparison2, currentSequence);
					if (overlap2 >= length * 0.5 && overlap2 >= comparisonLength2 * 0.5) {
						connected[i][0] = true;
						connected[j][1] = true;
					}
				}
			}
		}
		// walk through the boolean array. If at line i, both booleans are false, that means 
		// the current sequence doesn't connect to any of the other sequences, this line gives the 
		// wrong DNA.
		ArrayList<String> wrong = new ArrayList<String>();
		for (int i = 0; i < sequence.size(); i++) {
			if ((connected[i][0] | connected[i][1]) == false) {
				wrong.add(sequence.get(i));
			}
		}
		String[] wrongDNA = new String[wrong.size()];
		wrongDNA = wrong.toArray(wrongDNA);
		return  wrongDNA;
	}


	/* Part C */

	// returns the concatenation of two strings after merging the overlap
	public static String stitch (String a, String b) {
		int overlap = longestOverlap (a, b);
		return (a + b.substring(overlap));
	}
	
	// find the start index of the sequence
	public static int findStart (String[] sequence) {
		boolean[] hasHead = new boolean[sequence.length];
		for (int i = 0; i < sequence.length; i++) {
			String currentSequence = sequence[i];
			int length = currentSequence.length();
			for (int j = 0; j < sequence.length; j++) {
				if (j != i) {
					String comparison = sequence[j];
					int comparisonLength = comparison.length();
					int overlap = longestOverlap(comparison, currentSequence);
					if (overlap >= length * 0.5 && overlap >= comparisonLength * 0.5) {
						hasHead[i] = true;
					}
				}
			}
		}
		int start = -1;
		// the starting sequence would not have a "head", a sequence that comes before it
		for (int i = 0; i < hasHead.length; i++) {
			if (hasHead[i] == false) start = i;
		}
		return start;
	}

	private static boolean[] checked; // if the sequence has already been included in the path
	private static ArrayList<Integer> path; // the path of index

	// assmbles the sequence of DNAs into one long String of DNAs
	public static String assemble (String[] sequence) {
		checked = new boolean[sequence.length];
		path = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> connected = new HashMap<Integer, ArrayList<Integer>>(); // note the next legal move

		// initialize the HashMap connected
		for (int i = 0; i < sequence.length; i++) {	
			String currentSequence = sequence[i];
			int length = currentSequence.length();
			if (!connected.containsKey(i)) {
				ArrayList<Integer> next = new ArrayList<Integer>();
				connected.put(i, next);
			}
			for (int j = 0; j < sequence.length; j++) {
				if (j != i) {
					String comparison = sequence[j];
					int comparisonLength = comparison.length();
					int overlap = longestOverlap(currentSequence, comparison);
					if (overlap >= length * 0.5 && overlap >= comparisonLength * 0.5) {
						connected.get(i).add(j);
					}
				}
			}
		}

		int start = findStart(sequence);
		int end = 0;
		// find the end of the DNA by looking for a String that doesn't have any read after it
		for (int i = 0; i < connected.size(); i++) {
			if (connected.get(i).size() == 0) {
				end = i;
			}
		}
		// call DFS
		DFS(start, connected, sequence);
		String result = "";
		
		// merge the sequences together
		for (int i = 0; i < path.size(); i++) {
			result = stitch(result, sequence[path.get(i)]);
			if (path.get(i) == end) break;
		}
		return result;
	}
	
	// DFS uses Depth-First Search to assemble the Strings together
	public static void DFS (int start, HashMap<Integer, ArrayList<Integer>> connected, String[] sequence) {
		if (checked[start]) {
			path.remove(start);
			checked[start] = false;
			return;
		}
		if (connected.get(start).size() == 0) {
			path.add(start);
			if (path.size() != sequence.length) {
				path.remove(start);
				checked[start] = false;
				return;
			}
		}
		checked[start] = true;
		path.add(start);
		for (int i:connected.get(start)) {
			DFS(i, connected, sequence);
		}
	}

	public static void main(String[] args) {
		/* testing for part A
		String read1 = args[0];
		String read2 = args[1];
 		int overlap = longestOverlap (read1, read2);
		System.out.println(overlap); */
		
	
		/* testing for part B 
		ArrayList<String> sequence = new ArrayList<String>();
		sequence.add("TGAGTGGA");
		sequence.add("GGTGATGA");
		sequence.add("AAGGTGAG");
		sequence.add("TGGAGGTG");
		sequence.add("lalala"); 
		String[] wrongRow = findWrongDNA (sequence);
		for (int i = 0; i < wrongRow.length; i++) {
			System.out.println(wrongRow[i]);
		} */

		/* testing for part C
		ArrayList<String> sequence = new ArrayList<String>();
		sequence.add("TGAGTGGA");
		sequence.add("GGTGATGA");
		sequence.add("AAGGTGAG");
		sequence.add("TGGAGGTG");

		String[] input = new String[sequence.size()];
		input = sequence.toArray(input);
		String result = assemble(input);
		System.out.println(result); */
		

		/* Running the program */

		// read in input file
		Scanner scan = new Scanner(System.in);
		ArrayList<String> sequence = new ArrayList<String>();
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			sequence.add(line);
		}
		scan.close();

		// run findWrongDNA to remove the wrong line of DNA
		String[] wrongRow = findWrongDNA (sequence);
		for (int i = 0; i < wrongRow.length; i++) {
			sequence.remove(wrongRow[i]);
		}
		String[] input = new String[sequence.size()];
		input = sequence.toArray(input);

		// run assemble to return the full DNA sequence
		String result = assemble(input);
		System.out.println(result);

	}
}
