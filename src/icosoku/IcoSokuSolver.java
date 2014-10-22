package icosoku;

public class IcoSokuSolver {

	private static IcoSoku icosoku;

	public static void main(String[] args) {
		// example data
		int[] numbers = { 1, 3, 10, 7, 5, 4, 11, 6, 12, 8, 9, 2 };
		int[] numbers2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		int[] numbers3 = { 6, 12, 9, 10, 7, 2, 8, 1, 3, 5, 4, 11 };
		int[] numbers4 = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		int[] numbers5 = { 9, 4, 10, 12, 7, 8, 5, 6, 2, 1, 3, 11 };
		int[] numbers6 = { 1, 11, 2, 9, 4, 7, 6, 5, 8, 3, 10, 12 };

		long start = System.currentTimeMillis();

		icosoku = new IcoSoku(numbers6);
		System.out.println(backtrack(0));

		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
	}

	public static boolean backtrack(int stage) {
		// if final stage check the solution
		if (stage == 20) {
			boolean finished = icosoku.checkAll();
			if (finished) {
				System.out.println("Solution found!");
				return true;
			} else {
				return false;
			}
		} else {
			// check if icosoku can still be solved
			if (icosoku.doable()) {
				// try each available piece in each stage
				for (int piece = 0; piece < 20; piece++) {
					if (icosoku.isPieceAvailable(piece)) {
						// try each orientation in each stage
						for (int i = 0; i < 3; i++) {
							icosoku.setPiece(
									icosoku.areaWithSum[stage][0], piece, i);
							// print solution
							if (backtrack(stage + 1)) {
								System.out.println("area "
										+ icosoku.areaWithSum[stage][0]
										+ " piece " + piece + " orientation "
										+ i);
								return true;
							} else {
								icosoku.removePiece(icosoku.areaWithSum[stage][0]);
							}
						}
					}
				}
			}
			return false;
		}
	}

}