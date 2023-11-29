package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {

	private static final int BOARD_SIZE = 8;

	private String[][] board = {
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬜" },
			{ "⬜", "⬜", "⬜", "⬛", "⬛", "⬛", "⬜", "⬜" },
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜" },
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜" },
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜", "⬜" },
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜" },
			{ "⬜", "⬜", "⬜", "⬜", "⬜", "⬜", "⬛", "⬜" },
			{ "⬜", "⬜", "⬜", "⬛", "⬛", "⬛", "⬜", "⬜" } };

	private int player = 0;

	public void play() {
		displayBoard();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		addCarsToRow(1, 3, 5);
		addCarsToRow(2, 2, 5);
		addCarsToRow(3, 6);
		addCarsToRow(4, 5);
		addCarsToRow(5, 4);
		addCarsToRow(6, 3);
		addCarsToRow(7, 2, 3, 4, 5, 6);

		displayBoard();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		clearRow(0);
		clearRow(1, 4);
		clearRow(2, 3);
		clearRow(3, 4);
		clearRow(4, 4);
		clearRow(5, 4);
		clearRow(6, 4);
		clearRow(7);

		boolean playing = true;
		while (playing) {
			try {
				Random r = new Random();
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int newCar = r.nextInt(BOARD_SIZE);
				board[0][newCar] = "🚙";
				board[7][player] = "🚗";

				displayBoard();

				// input
				String key = br.readLine();

				if (key.equals("q")) {
					playing = false;
					break;
				} else if (key.equals("a") && player > 0) {
					movePlayer(-1);
				} else if (key.equals("d") && player < BOARD_SIZE - 1) {
					movePlayer(1);
				}

				if (board[7][player].equals("🚙") || board[6][player].equals("🚙")) {
					playing = false;
					System.out.println("Perdiste!");

					// move cars down
					for (int i = 0; i < BOARD_SIZE - 1; i++) {
						board[7 - i] = board[6 - i].clone();
					}

					clearRow(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void displayBoard() {
		for (String[] row : board) {
			for (String elem : row)
				System.out.print(" " + elem + " ");
			System.out.println("");
		}
	}

	private void addCarsToRow(int row, int... columns) {
		for (int col : columns) {
			board[row][col] = "⬛";
		}
	}

	private void clearRow(int row, int... excludeColumns) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (!contains(excludeColumns, i)) {
				board[row][i] = "⬜";
			}
		}
	}

	private void movePlayer(int offset) {
		board[7][player] = "⬜";
		player += offset;
	}

	private boolean contains(int[] array, int target) {
		for (int value : array) {
			if (value == target) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}
