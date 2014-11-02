package icosoku;

public class IcoSokuSolver {
	private IcoSoku icosoku;
	private int backtrackCalls;

	public IcoSokuSolver(IcoSoku icosoku) {
		this.icosoku = icosoku;
	}

	public void solve() {
		for (int i = 0; i < 12; i++) {
			System.out.print("|" + icosoku.edgeNumber(i));
		}
		System.out.println("|");
		// solve with timing
		long start = System.currentTimeMillis();
		System.out.println(backtrack(0));
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		System.out.println("Number of backtrack method calls: " + backtrackCalls);
	}

	public boolean backtrack(int stage) {
		backtrackCalls++;
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
							icosoku.setPiece(icosoku.areaWithSum[stage][0], piece, i);
							// print solution
							if (backtrack(stage + 1)) {
								System.out.println("area " + icosoku.areaWithSum[stage][0] + " piece " + piece
										+ " orientation " + i);
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