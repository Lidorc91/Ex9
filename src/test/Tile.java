package test;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Tile {
	public final char letter;
	public final int score;

	private Tile(char l, int s) {
		this.letter = l;
		this.score = s;
	}

	@Override
	public int hashCode() {
		return Objects.hash(letter, score);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return letter == other.letter && score == other.score;
	}

	public static class Bag {
		private final int[] _tileAmount = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2,
				1 };
		private final int[] _maxTilesAllowed = _tileAmount.clone();
		private final int[] _tileScores = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4,
				10 };
		private final ArrayList<Tile> _tiles = new ArrayList<>(26);

		public Bag() {
			for (int i = 0; i < 25; i++) {
				_tiles.add(i, new Tile((char) ('A' + i), _tileScores[i]));
			}
		}

		public Tile getRand() {
			if (isEmpty()) {
				return null;
			}
			Random r = new Random();
			int index = r.nextInt(25);
			while (true) {
				if (_tileAmount[index] > 0) {
					_tileAmount[index]--;
					return _tiles.get(index);
				}
				index = r.nextInt(25);
			}
		}

		public Tile getTile(char letter) {
			int index = letter - 'A';
			if (index < 0 || index > 25) {
				return null;
			}
			return _tiles.get(index);
		}

		public void put(Tile t) {
			int index = t.letter - 'A';
			if (_tileAmount[index] < _maxTilesAllowed[index]) {
				_tileAmount[index]++;
			}
		}

		public int size() {
			int sum = 0;
			for (int i : _tileAmount) {
				sum += i;
			}
			return sum;
		}

		public int[] getQuantities() {
			return _tileAmount.clone();
		}

		// FUNCTIONAL METHODS
		private boolean isEmpty() { // Checks if bag in empty
			for (int i : _tileAmount) {
				if (i > 0) {
					return false;
				}
			}
			return true;
		}

	}
}
