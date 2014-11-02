package icosoku;

public class IcoSoku {
	private static int[][] edgeAreas = { { 0, 1, 2, 3, 4 }, // edge 0
			{ 4, 13, 14, 5, 0 }, { 1, 0, 5, 6, 7 }, // edge 2
			{ 1, 2, 7, 8, 9 }, { 2, 3, 9, 10, 11 }, // edge 4
			{ 3, 4, 11, 12, 13 }, { 5, 6, 14, 15, 19 }, // edge 6
			{ 6, 7, 8, 15, 16 }, { 8, 9, 10, 16, 17 }, // edge 8
			{ 10, 11, 12, 17, 18 }, { 12, 13, 14, 18, 19 }, // edge 10
			{ 15, 16, 17, 18, 19 } };
	private static int[][] edgeAreasOrientation = { { 0, 0, 0, 0, 0 }, // edge 0
			{ 2, 2, 0, 0, 1 }, { 1, 2, 2, 0, 0 }, { 2, 1, 2, 0, 0 }, // edge 3
			{ 2, 1, 2, 0, 0 }, { 2, 1, 2, 0, 0 }, { 1, 1, 2, 0, 2 }, // edge 6
			{ 2, 1, 1, 2, 0 }, { 2, 1, 1, 2, 0 }, { 2, 1, 1, 2, 0 }, // edge 9
			{ 2, 1, 1, 2, 0 }, { 1, 1, 1, 1, 1 } };
	private static int[][] pieceNumbers = { { 0, 0, 0 }, // piece 0
			{ 0, 0, 1 }, { 0, 0, 2 }, { 0, 0, 3 }, { 0, 1, 1 }, // piece 4
			{ 0, 1, 2 }, { 0, 1, 2 }, { 0, 1, 2 }, { 0, 2, 1 }, // piece 8
			{ 0, 2, 1 }, { 0, 2, 1 }, { 0, 2, 2 }, { 0, 3, 3 }, // piece 12
			{ 1, 1, 1 }, { 1, 2, 3 }, { 1, 2, 3 }, { 1, 3, 2 }, // piece 16
			{ 1, 3, 2 }, { 2, 2, 2 }, { 3, 3, 3 } };
	public int[][] areaWithSum = new int[20][2];
	private int[] edgeNumbers = new int[12];
	private boolean[] isAreaOccupied = new boolean[20];
	private boolean[] isPieceAvailable = new boolean[20];
	private int[] areaHasPiece = new int[20];
	private int[] areaHasPieceOriantation = new int[20];

	public IcoSoku(int[] inputEdgeNumbers) {
		// check if 12 input edge numbers were given
		if (inputEdgeNumbers.length != 12) {
			throw new IllegalArgumentException();
		} else {
			// check if input edge numbers are valid (1 to 12, no duplicates)
			boolean[] numberTest = new boolean[12];
			for (int i = 0; i < 12; i++) {
				numberTest[i] = false;
			}
			for (int i = 0; i < 12; i++) {
				if (inputEdgeNumbers[i] < 1 || inputEdgeNumbers[i] > 12) {
					throw new IllegalArgumentException();
				} else {
					edgeNumbers[i] = inputEdgeNumbers[i];
					numberTest[inputEdgeNumbers[i] - 1] = true;
				}
			}
			for (int i = 0; i < 12; i++) {
				if (!numberTest[i]) {
					throw new IllegalArgumentException();
				}
			}
			// initialize isAreaOccupied, isPieceAvailable, areaWithSum
			for (int i = 0; i < 20; i++) {
				isAreaOccupied[i] = false;
				isPieceAvailable[i] = true;
				areaWithSum[i][0] = i;
			}
			// calculate sum of the edge numbers for each area
			areaWithSum[0][1] = edgeNumbers[0] + edgeNumbers[1] + edgeNumbers[2];
			areaWithSum[1][1] = edgeNumbers[0] + edgeNumbers[2] + edgeNumbers[3];
			areaWithSum[2][1] = edgeNumbers[0] + edgeNumbers[3] + edgeNumbers[4];
			areaWithSum[3][1] = edgeNumbers[0] + edgeNumbers[4] + edgeNumbers[5];
			areaWithSum[4][1] = edgeNumbers[0] + edgeNumbers[5] + edgeNumbers[1];
			areaWithSum[5][1] = edgeNumbers[1] + edgeNumbers[6] + edgeNumbers[2];
			areaWithSum[6][1] = edgeNumbers[2] + edgeNumbers[6] + edgeNumbers[7];
			areaWithSum[7][1] = edgeNumbers[2] + edgeNumbers[7] + edgeNumbers[3];
			areaWithSum[8][1] = edgeNumbers[3] + edgeNumbers[7] + edgeNumbers[8];
			areaWithSum[9][1] = edgeNumbers[3] + edgeNumbers[8] + edgeNumbers[4];
			areaWithSum[10][1] = edgeNumbers[4] + edgeNumbers[8] + edgeNumbers[9];
			areaWithSum[11][1] = edgeNumbers[4] + edgeNumbers[9] + edgeNumbers[5];
			areaWithSum[12][1] = edgeNumbers[5] + edgeNumbers[9] + edgeNumbers[10];
			areaWithSum[13][1] = edgeNumbers[5] + edgeNumbers[10] + edgeNumbers[1];
			areaWithSum[14][1] = edgeNumbers[1] + edgeNumbers[10] + edgeNumbers[6];
			areaWithSum[15][1] = edgeNumbers[6] + edgeNumbers[11] + edgeNumbers[7];
			areaWithSum[16][1] = edgeNumbers[7] + edgeNumbers[11] + edgeNumbers[8];
			areaWithSum[17][1] = edgeNumbers[8] + edgeNumbers[11] + edgeNumbers[9];
			areaWithSum[18][1] = edgeNumbers[9] + edgeNumbers[11] + edgeNumbers[10];
			areaWithSum[19][1] = edgeNumbers[10] + edgeNumbers[11] + edgeNumbers[6];
			// sort areas by their sum
			quicksort(0, 19);
		}
	}

	public boolean isAreaOccupied(int area) {
		if (area < 0 || area > 19) {
			throw new IllegalArgumentException();
		} else {
			return isAreaOccupied[area];
		}
	}

	public boolean isPieceAvailable(int piece) {
		if (piece < 0 || piece > 19) {
			throw new IllegalArgumentException();
		} else {
			return isPieceAvailable[piece];
		}
	}

	public int numberAtPiece(int piece, int orientation) {
		if (piece < 0 || piece > 19 || orientation < 0 || orientation > 2) {
			throw new IllegalArgumentException();
		} else {
			return pieceNumbers[piece][orientation];
		}
	}

	public boolean setPiece(int area, int piece, int orientation) {
		if (area < 0 || area > 19 || piece < 0 || piece > 19 || orientation < 0 || orientation > 2) {
			throw new IllegalArgumentException();
		} else if (isAreaOccupied[area] || !isPieceAvailable[piece]) {
			return false;
		} else {
			areaHasPiece[area] = piece;
			areaHasPieceOriantation[area] = orientation;
			isAreaOccupied[area] = true;
			isPieceAvailable[piece] = false;
			return true;
		}
	}

	public boolean removePiece(int piece) {
		if (piece < 0 || piece > 19) {
			throw new IllegalArgumentException();
		} else if (!isAreaOccupied[piece]) {
			return false;
		} else {
			isAreaOccupied[piece] = false;
			isPieceAvailable[areaHasPiece[piece]] = true;
			return true;
		}
	}

	public void removeAllPieces() {
		for (int i = 0; i < 20; i++) {
			isAreaOccupied[i] = false;
			isPieceAvailable[i] = true;
		}
	}

	public int edgeNumber(int edge) {
		if (edge < 0 || edge > 11) {
			throw new IllegalArgumentException();
		} else {
			return edgeNumbers[edge];
		}
	}

	public int sumAtEdge(int edge) {
		if (edge < 0 || edge > 11) {
			throw new IllegalArgumentException();
		} else {
			int sum = 0;
			for (int i = 0; i < 5; i++) {
				int area = edgeAreas[edge][i];
				int areaOrientation = edgeAreasOrientation[edge][i];
				if (isAreaOccupied[area]) {
					int piece = areaHasPiece[area];
					int orientation = areaHasPieceOriantation[area];
					int pieceIndex = (areaOrientation + orientation) % 3;
					sum += pieceNumbers[piece][pieceIndex];
				}
			}
			return sum;
		}
	}

	public int pieceCountAtEdge(int edge) {
		if (edge < 0 || edge > 11) {
			throw new IllegalArgumentException();
		} else {
			int count = 0;
			for (int i = 0; i < 5; i++) {
				if (isAreaOccupied[edgeAreas[edge][i]])
					count++;
			}
			return count;
		}
	}

	public boolean checkAll() {
		for (int edge = 0; edge < 12; edge++) {
			if (pieceCountAtEdge(edge) < 5)
				return false;
			if (sumAtEdge(edge) != edgeNumber(edge))
				return false;
		}
		return true;
	}

	public boolean doable() {
		for (int i = 0; i < edgeNumbers.length; i++) {
			// compare sum of pieces with the number at full edges
			if (pieceCountAtEdge(i) == 5) {
				if (sumAtEdge(i) != edgeNumber(i)) {
					return false;
				}
			} else {
				// stop if piece sum is larger then edge number
				if (sumAtEdge(i) > edgeNumber(i)) {
					return false;
				}
				// check if edge number can be reached
				int differenceAtEdge = edgeNumber(i) - sumAtEdge(i);
				int differenceAtPieces = 5 - pieceCountAtEdge(i);
				boolean notDoableWith3 = Math.ceil(differenceAtEdge / 3.0) > differenceAtPieces;
				if (notDoableWith3) {
					return false;
				}
			}
		}
		return true;
	}

	private void quicksort(int left, int right) {
		if (left < right) {
			int divider = divide(left, right);
			quicksort(left, divider - 1);
			quicksort(divider + 1, right);
		}
	}

	private int divide(int left, int right) {
		int i = left;
		int j = right - 1;
		int pivot = areaWithSum[right][1];
		do {
			while (areaWithSum[i][1] <= pivot && i < right) {
				i++;
			}
			while (areaWithSum[j][1] >= pivot && j > left) {
				j--;
			}
			if (i < j) {
				int[] swap = areaWithSum[i];
				areaWithSum[i] = areaWithSum[j];
				areaWithSum[j] = swap;
			}
		} while (i < j);
		if (areaWithSum[i][1] > pivot) {
			int[] swap = areaWithSum[i];
			areaWithSum[i] = areaWithSum[right];
			areaWithSum[right] = swap;
		}
		return i;
	}
}
