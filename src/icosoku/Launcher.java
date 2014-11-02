package icosoku;

public class Launcher {
	public static void main(String[] args) {
		if (args.length == 1) {
			IcoSoku icosoku;
			IcoSokuSolver solver;
			// example data
			int[] numbers1 = { 1, 3, 10, 7, 5, 4, 11, 6, 12, 8, 9, 2 };
			int[] numbers2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
			int[] numbers3 = { 6, 12, 9, 10, 7, 2, 8, 1, 3, 5, 4, 11 };
			int[] numbers4 = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
			int[] numbers5 = { 9, 4, 10, 12, 7, 8, 5, 6, 2, 1, 3, 11 };
			int[] numbers6 = { 1, 11, 2, 9, 4, 7, 6, 5, 8, 3, 10, 12 };
			switch (args[0]) {
			case "numbers1":
				icosoku = new IcoSoku(numbers1);
				break;
			case "numbers2":
				icosoku = new IcoSoku(numbers2);
				break;
			case "numbers3":
				icosoku = new IcoSoku(numbers3);
				break;
			case "numbers4":
				icosoku = new IcoSoku(numbers4);
				break;
			case "numbers5":
				icosoku = new IcoSoku(numbers5);
				break;
			case "numbers6":
				icosoku = new IcoSoku(numbers6);
				break;
			default:
				String[] temp = args[0].split(",");
				if (temp.length != 12) {
					throw new IllegalArgumentException();
				} else {
					int[] input = new int[temp.length];
					for (int i = 0; i < temp.length; i++) {
						input[i] = Integer.parseInt(temp[i]);
					}
					icosoku = new IcoSoku(input);
				}
			}
			solver = new IcoSokuSolver(icosoku);
			solver.solve();
		} else {
			System.out.println("Provide an array with the numbers from 1 to 12\n"
					+ "or use the example data: numbers[1-6]");
		}
	}
}
